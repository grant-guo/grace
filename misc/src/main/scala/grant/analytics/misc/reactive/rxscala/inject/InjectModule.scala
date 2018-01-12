package grant.analytics.misc.reactive.rxscala.inject

import java.io.File
import java.sql.Connection

import com.google.inject.{AbstractModule, Provides}
import grant.analytics.misc.reactive.rxscala.compress.UntarFile
import grant.analytics.misc.reactive.rxscala.compress.impl.UntarFileImpl
import grant.analytics.misc.reactive.rxscala.conf.{Configuration, DatabaseConfig}
import grant.analytics.misc.reactive.rxscala.conf.impl.ConfigurationImpl
import grant.analytics.misc.reactive.rxscala.context.Context
import grant.analytics.misc.reactive.rxscala.context.impl.ContextImpl
import grant.analytics.misc.reactive.rxscala.download.DownloadFiles
import grant.analytics.misc.reactive.rxscala.download.impl.DownloadFilesImpl
import grant.analytics.misc.reactive.rxscala.extractor.Extractor
import grant.analytics.misc.reactive.rxscala.extractor.impl.{FileExtractor, Key_Value}

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
class InjectModule extends AbstractModule {
  override def configure() = {}

  @Provides
  def getConfiguration(): Configuration = {
    new ConfigurationImpl("application.conf")
  }

  @Provides
  def getContext(conf: Configuration): Context = {
    new ContextImpl(conf)
  }

  @Provides
  def getDownloadFiles(conf:Configuration):DownloadFiles = {
    val tmpDataDir = new File(System.getProperty("java.io.tmpdir"))
    new DownloadFilesImpl(conf.getFTPConfig(), tmpDataDir)
  }

  @Provides
  def getUntarFile(): UntarFile = {
    val target_file = new File("")
    new UntarFileImpl(target_file)
  }

  @Provides
  def getExtractor(): Extractor[Key_Value] = {
    new FileExtractor
  }

  @Provides
  def getDatabaseConn(dbConfig: DatabaseConfig):Connection = {
    ???
  }
}
