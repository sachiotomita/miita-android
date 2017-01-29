GIT_HASH=`git rev-parse --short HEAD`

curl -F "file=@app/build/outputs/apk/app-staging.apk" \
     -F "token=${DEPLOY_GATE_API_KEY}" \
     -F "message=https://github.com/naoto0822/miita-android/tree/${GIT_HASH}" \
     https://deploygate.com/api/users/naoto0822/apps
