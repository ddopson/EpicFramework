cd ~/Library/Logs/DiagnosticReports/
FILE=`ls -tr | egrep '\.crash$' | tail -n1`
less "$FILE"
