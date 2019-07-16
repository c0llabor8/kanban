ENV="$PWD/.env"
if [ -f "$ENV" ]; then
    source $ENV
    parse-dashboard --appId "c0llabor8" --masterKey "$PARSE_MASTER_KEY" --serverURL "http://c0llabor8.herokuapp.com/parse"
else
    echo "Please create $ENV and set PARSE_MASTER_KEY"
fi
