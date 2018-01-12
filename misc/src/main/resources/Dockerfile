FROM jupyter/all-spark-notebook
USER root

RUN apt-get update
RUN apt-get install -y net-tools iputils-ping iptables sudo software-properties-common debconf-utils

#RUN add-apt-repository ppa:webupd8team/java && apt-get update
#RUN echo "oracle-java8-installer shared/accepted-oracle-license-v1-1 select true" | debconf-set-selections
#RUN echo "oracle-java8-installer shared/accepted-oracle-license-v1-1 seen true" | debconf-set-selections
#RUN apt-get install -y oracle-java8-installer

ADD scala-2.11.11.tgz /usr/local/

ENV SCALA_HOME=/usr/local/scala-2.11.11
ENV PATH="$SCALA_HOME/bin:$PATH"

RUN pip install toree
RUN jupyter toree install --replace --sys-prefix --interpreters=Scala,PySpark,SparkR,SQL --spark_opts="--master spark://10.0.0.6:7077 --deploy-mode client "

COPY install_sparkmagic.sh /usr/local/
RUN bash /usr/local/install_sparkmagic.sh
RUN rm /usr/local/install_sparkmagic.sh

RUN if [ ! -d /home/$NB_USER/.sparkmagic ];then mkdir /home/$NB_USER/.sparkmagic;fi
COPY config.json /home/jovyan/.sparkmagic/
RUN chown -R $NB_USER /home/$NB_USER/.sparkmagic

USER $NB_USER