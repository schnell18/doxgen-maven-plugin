%!TEX TS-program = xelatex
%!TEX encoding = UTF-8 Unicode
%!TeX root = ../manual.tex

<#list models as model>
\subsubsection{[=model.name]}
  \label{subsec:subsubSec[=model.name]}

  \begin{VerbatimOut}{\jobname.vrb}
  \begin{longtable}{c|c|X}
    \caption{\emph{[=model.name]对象字段表}}
    \label{tab:mdl-[=model.qualifiedName]} \\
    \thickhline
    \multicolumn{1}{c|}{\textbf{字段}} &
    \multicolumn{1}{c|}{\textbf{类型}} &
    \multicolumn{1}{c}{\textbf{描述}} \\
    \thickhline
    \endfirsthead
    \multicolumn{3}{r}{{\tablename\ \thetable{} -- \emph{续上一页}}} \\
    \thickhline
    \multicolumn{1}{c|}{\textbf{字段}} &
    \multicolumn{1}{c|}{\textbf{类型}} &
    \multicolumn{1}{c}{\textbf{描述}} \\
    \endhead
    \multicolumn{3}{r}{{\tablename\ \thetable{} -- \emph{下一页续}}} \\
    \endfoot
    \endlastfoot

    <#assign fields = model.fields>
    <#list fields as field>
      [=field.name] & [=field.type] & <#if field.description??>[=field.latexDescription]<#else>\todobox{请补充参数说明}</#if> \\\hline
    </#list>

    \thickhline
  \end{longtable}
  \end{VerbatimOut}
  \LTXtable{\textwidth}{\jobname.vrb}

</#list>

% vim: set ai nu nobk expandtab ts=4 sw=2 tw=72 syntax=tex :
