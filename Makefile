bootstrap:
	touch debug.properties
	echo "qiitaClientID=" >> debug.properties
	echo "qiitaClientSecret=" >> debug.properties

prepare:
	cp ./deploy/miita/android/fabric.properties ./app
	cp ./deploy/miita/android/google-services.json ./app/src/release

deploy-staging:
	./deploy-staging.sh
