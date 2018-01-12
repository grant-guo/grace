package grant.analytics.misc.reactive.rxscala.compress.impl

import java.io.File

import grant.analytics.misc.reactive.rxscala.compress.UntarFile
import org.codehaus.plexus.archiver.tar.TarGZipUnArchiver
import org.codehaus.plexus.logging.console.ConsoleLoggerManager

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
class UntarFileImpl(target_dir:File) extends UntarFile{
  override def untar(input: File) = {
    val archiver = new TarGZipUnArchiver()
    val manager = new ConsoleLoggerManager()
    manager.initialize()
    archiver.enableLogging(manager.getLoggerForComponent("untar_file_impl"))
    archiver.setSourceFile(input)
    archiver.setDestDirectory(target_dir)
    archiver.extract()
    val untar = new File(target_dir, input.getName.substring(0, input.getName.indexOf(".tar.gz")))
    input.delete()
    untar
  }
}
