#! /bin/sh
pip install sparkmagic
jupyter nbextension enable --py --sys-prefix widgetsnbextension

IFS=': '

read -r -a array <<< $(pip show sparkmagic|grep Location)

unset IFS

cd ${array[1]}

jupyter-kernelspec install sparkmagic/kernels/sparkkernel
jupyter-kernelspec install sparkmagic/kernels/pysparkkernel
jupyter-kernelspec install sparkmagic/kernels/pyspark3kernel
jupyter-kernelspec install sparkmagic/kernels/sparkrkernel
jupyter serverextension enable --py sparkmagic