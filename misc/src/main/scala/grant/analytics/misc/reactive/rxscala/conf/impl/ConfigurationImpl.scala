package grant.analytics.misc.reactive.rxscala.conf.impl

import com.typesafe.config.{Config, ConfigFactory}
import grant.analytics.misc.reactive.rxscala.conf.{Configuration, DatabaseConfig, FTPConfig}

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
class ConfigurationImpl(conf_path:String) extends Configuration{

  private lazy val (database, ftp) = loadConfig()

  private def loadConfig(): (DatabaseConfig, FTPConfig) = {
    val config = ConfigFactory.load(conf_path)
    (
      DatabaseConfig(
        config.getString("grant.analytics.misc.reactive.rxjava.database.host"),
        config.getString("grant.analytics.misc.reactive.rxjava.database.user"),
        config.getString("grant.analytics.misc.reactive.rxjava.database.password"),
        config.getString("grant.analytics.misc.reactive.rxjava.database.db_name")
      ),
      FTPConfig(
        config.getString("grant.analytics.misc.reactive.rxjava.ftp.host"),
        config.getString("grant.analytics.misc.reactive.rxjava.ftp.user"),
        config.getString("grant.analytics.misc.reactive.rxjava.ftp.password"),
        config.getString("grant.analytics.misc.reactive.rxjava.ftp.path")
      )
    )
  }

  override def getDatabaseConfig() = database

  override def getFTPConfig() = ftp
}
