for f in *.html; do
mv "$f" "$(basename "$f" .html).jsp"
done
