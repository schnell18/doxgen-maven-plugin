%!TEX TS-program = xelatex
%!TEX encoding = UTF-8 Unicode

\documentclass[12pt]{article}
\usepackage{ctex}
\usepackage{geometry}
\usepackage{float}
\usepackage{xcolor}
\usepackage{amssymb}
\usepackage{tikz}
\usepackage{minted}
\usepackage{caption}
\usepackage{ltxtable}
\usepackage{fancyvrb}
\usepackage{tabularx}
\usepackage{smartdiagram}

\usepackage{graphicx}
\graphicspath{{./images/}}

\usepackage{fancyhdr}
\setlength{\headheight}{25pt}
\pagestyle{fancy}
\fancyhf{}
\fancyhead[R]{\includegraphics[scale=0.2]{logo}}
\fancyhf[FLE,FRO]{\thepage}

\usepackage{fontspec,xltxtra,xunicode,xeCJK}
\usepackage[
  colorlinks=true,
  linkcolor=blue,
  bookmarksnumbered=true,
  CJKbookmarks=true,
  bookmarksopen=true]{hyperref}

\definecolor{titlepagecolor}{cmyk}{1,.60,0,.40}
\DeclareFixedFont{\titlefont}{T1}{ppl}{b}{it}{0.5in}

\newcommand{\todobox}[1]{
  \fcolorbox{red}{yellow}{#1}
}

\newcommand{\mytitle}[1]{
  {\bfseries \zihao{1} #1}\par
}

\newcommand{\mysubtitle}[1]{
  {\zihao{3} \emph{#1}}\par
}

\newminted{java}{
  autogobble,
  breaklines,
  frame=leftline,
  framerule=1.2pt,
  framesep=1em,
  linenos,
  fontsize=\footnotesize
}

\makeatletter
\def\printauthor{%
    {\large \@author}}
\makeatother

\newcommand\titlepagedecoration{%
\begin{tikzpicture}[remember picture,overlay,shorten >= -10pt]

\coordinate (aux1) at ([yshift=-15pt]current page.north east);
\coordinate (aux2) at ([yshift=-410pt]current page.north east);
\coordinate (aux3) at ([xshift=-4.5cm]current page.north east);
\coordinate (aux4) at ([yshift=-150pt]current page.north east);

\begin{scope}[titlepagecolor!40,line width=12pt,rounded corners=12pt]
\draw
  (aux1) -- coordinate (a)
  ++(225:5) --
  ++(-45:5.1) coordinate (b);
\draw[shorten <= -10pt]
  (aux3) --
  (a) --
  (aux1);
\draw[opacity=0.6,titlepagecolor,shorten <= -10pt]
  (b) --
  ++(225:2.2) --
  ++(-45:2.2);
\end{scope}
\draw[titlepagecolor,line width=8pt,rounded corners=8pt,shorten <= -10pt]
  (aux4) --
  ++(225:0.8) --
  ++(-45:0.8);
\begin{scope}[titlepagecolor!70,line width=6pt,rounded corners=8pt]
\draw[shorten <= -10pt]
  (aux2) --
  ++(225:3) coordinate[pos=0.45] (c) --
  ++(-45:3.1);
\draw
  (aux2) --
  (c) --
  ++(135:2.5) --
  ++(45:2.5) --
  ++(-45:2.5) coordinate[pos=0.3] (d);
\draw
  (d) -- +(45:1);
\end{scope}
\end{tikzpicture}%
}

\makeatletter
\newcommand{\thickhline}{%
    \noalign {\ifnum 0=`}\fi \hrule height 1.5pt
    \futurelet \reserved@a \@xhline
}
\makeatother
\renewcommand{\listoflistingscaption}{源码清单}

\author{%
    \zihao{5}
    <#list authors as author>
    [=author.name] \\
    [=author.affiliation] \\
    \texttt{[=author.email]}\vspace{15pt} \\
    </#list>
}

\begin{document}

\begin{titlepage}
\begin{minipage}{0.8\linewidth}
  \vspace*{4cm}
  \begin{center}
    \noindent
    \mytitle{<#if title??>[=title]<#else>\todobox{未知标题请设置}</#if>}
    \rule{\linewidth}{0.2ex}\par
    \mysubtitle{<#if subTitle??>[=subTitle] V[=version]<#else>\todobox{未知副标题请设置}</#if>}
  \end{center}
\end{minipage}
\null \vfill
\hfill
\begin{minipage}{0.55\linewidth}
  \begin{flushright}
    \printauthor
  \end{flushright}
\end{minipage}
%
\begin{minipage}{0.02\linewidth}
  \rule{1pt}{160pt}
\end{minipage}
\titlepagedecoration
\end{titlepage}


<#-- include revision history page, allow absence -->
<#if revisionTpl??>
  <#include revisionTpl ignore_missing=true>
</#if>

\newpage
\tableofcontents
\newpage
\listoffigures
\newpage
\listoftables
\newpage
\listoflistings
\newpage

<#-- include all user content specified at runtime -->
<#if userContents??>
  <#list userContents as content>
    <#include content>
  </#list>
</#if>

<#if dubbos??>
  \section{Dubbo接口说明}
  \todobox{TODO: add general description here later}
  <#list dubbos as dubbo>
    \include{dubbo/[=dubbo]}
  </#list>
  <#if models??>
    \subsection{Dubbo接口引用的领域对象说明}
    \todobox{TODO: add general description here later}
    <#list models as model>
      \include{domain/[=model]}
    </#list>
  </#if>
</#if>

<#if mapis??>
\section{网关接口说明}
\todobox{TODO: add general description here later}
  <#list mapis as mapi>
  \include{mapi/[=mapi]}
  </#list>
  <#if mapimodels??>
  \subsection{网关接口引用的领域对象说明}
  \todobox{TODO: add general description here later}
    <#list mapimodels as mapimodel>
    \include{domain/[=mapimodel]}
    </#list>
  </#if>
</#if>

<#if datamodels??>
  % data model
  \section{数据模型}
  \todobox{TODO: add general description here later}
  <#list datamodels as datamodel>
    \input{datamodel/[=datamodel]}
  </#list>
</#if>

\end{document}

% vim: set ai nu nobk expandtab ts=4 sw=2 tw=72 syntax=tex :
