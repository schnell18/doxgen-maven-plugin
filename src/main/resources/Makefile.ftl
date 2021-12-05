all:    manual

# repeat four times
manual: manual.tex
	xelatex -shell-escape manual
	xelatex -shell-escape manual
	xelatex -shell-escape manual
	xelatex -shell-escape manual

clean:
	rm -fr *.aux *.log *.nav *.out *.pdf *.snm *.toc *.vrb *.lof *.lot *.lol _minted* *.pyg *.synctex.gz

