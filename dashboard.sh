ENV="./.env"
if [ -f "$ENV" ]; then
    source $ENV
    parse-dashboard --appId kan-banana --masterKey $PARSE_MASTER_KEY --serverURL "https://kan-banana.herokuapp.com/parse"
else
    echo "Please create $ENV and set PARSE_MASTER_KEY"
fi
