prepare:
	cp ./deploy/miita/android/fabric.properties ./app
	cp ./deploy/miita/android/google-services.json ./app/src/release

deploy-debug:
	./deploy-debug.sh
