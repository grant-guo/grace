package grant.analytics.misc.reactive.rxscala.download.impl

import java.io.File
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicLong

import com.google.common.collect.ImmutableList
import com.google.common.collect.ImmutableList.Builder
import com.google.common.util.concurrent.ThreadFactoryBuilder
import grant.analytics.misc.reactive.rxscala.conf.FTPConfig
import grant.analytics.misc.reactive.rxscala.download.DownloadFiles
import it.sauronsoftware.ftp4j.FTPClient
import rx.lang.scala.Observable
import rx.lang.scala.schedulers.IOScheduler
import rx.schedulers.Schedulers

import scala.collection.convert.WrapAsJava

/**
  **
  * Copyright (c) 2017 The Ontario Institute for Cancer Research. All rights reserved.
  **
  * This program and the accompanying materials are made available under the terms of the GNU Public License v3.0.
  * You should have received a copy of the GNU General Public License along with
  * this program. If not, see <http://www.gnu.org/licenses/>.
  **
  * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY
  * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
  * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT
  * SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
  * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED
  * TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
  * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER
  * IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
  * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
  *
  */
class DownloadFilesImpl(ftpConfig: FTPConfig, tmp_data_dir:File) extends DownloadFiles{

  private val sizeOfConnPool = 5
  private val parallel = 20
  private val executor = Executors.newFixedThreadPool(parallel, (new ThreadFactoryBuilder()).setNameFormat("download-thread-pool-%d").build)
  private val indexForFTPClient = new AtomicLong(0)
  private val builder: Builder[FTPClient] = ImmutableList.builder[FTPClient]()

  private lazy val ftpClients: ImmutableList[FTPClient] = generateFtpClients()
  override def download():Observable[File] = {
    Observable.defer(

        Observable.just(ftpConfig)
          .flatMap[FTPClient_Filenames](config => {
              val ftpClient = new FTPClient
              ftpClient.connect(ftpConfig.host)
              ftpClient.login(ftpConfig.user, ftpConfig.password)
              ftpClient.changeDirectory(ftpConfig.path)
              ftpClient.setType(FTPClient.TYPE_BINARY)

              Observable.just(
                FTPClient_Filenames(ftpClient, ftpClient.list("EGA*.tar.gz").toList.map(ftpFile => ftpFile.getName))
              )
            })
          .zipWith[List[FTPClient], List[String]](

              Observable.just[List[FTPClient]](
                Range(0, sizeOfConnPool-1).map(index => {
                  val ftpClient = new FTPClient
                  ftpClient.connect(ftpConfig.host)
                  ftpClient.login(ftpConfig.user, ftpConfig.password)
                  ftpClient.changeDirectory(ftpConfig.path)
                  ftpClient.setType(FTPClient.TYPE_BINARY)
                  ftpClient
                }).toList
              )
            ) {
              (pair: FTPClient_Filenames, list: List[FTPClient]) => {
                builder.add(pair.ftpClient).addAll(WrapAsJava.asJavaIterable(list))
                pair.names
              }
            }
          .flatMap[String](list => Observable.from(list))
          .flatMap(file => {
              Observable.just[String](file).observeOn(new IOScheduler()).flatMap[File](filename => {
                val localFile = new File(tmp_data_dir, filename)
                getFtpClient().download(filename, localFile)
                Observable.just(localFile)
              })
          })
    )
  }

  private def generateFtpClients():ImmutableList[FTPClient] = {
    builder.build()
  }

  private def getFtpClient() = {
    val index = indexForFTPClient.getAndIncrement
    ftpClients.get((index % sizeOfConnPool).toInt)
  }
}

case class FTPClient_Filenames(ftpClient:FTPClient, names:List[String])