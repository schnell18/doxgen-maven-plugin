%!TEX TS-program = xelatex
%!TEX encoding = UTF-8 Unicode
%!TeX root = ../manual.tex

\subsection{[=table.latexName]}
  \label{sec:subSec[=table.refName]}
表\ref{tab:[=table.refName]-tab}是<#if table.comment?length gt 0>[=table.latexComment]<#else>\todobox{TODO请补充此表的描述}</#if>的字段描述。

  \begin{VerbatimOut}{\jobname.vrb}
    \begin{longtable}{l|l|l|X}
      \caption{<#if table.comment?length gt 0>\emph{[=table.latexComment]}<#else>\todobox{TODO请补充此表的描述}</#if>}
      \label{tab:[=table.refName]-tab} \\
      \thickhline
      \multicolumn{1}{c|}{\textbf{字段}} &
      \multicolumn{1}{c|}{\textbf{数据类型}} &
      \multicolumn{1}{c|}{\textbf{可空}} &
      \multicolumn{1}{c}{\textbf{描述}} \\
      \thickhline
      \endfirsthead
      \multicolumn{4}{r}{{\tablename\ \thetable{} -- \emph{续上一页}}} \\
      \thickhline
      \multicolumn{1}{c|}{\textbf{字段}} &
      \multicolumn{1}{c|}{\textbf{数据类型}} &
      \multicolumn{1}{c|}{\textbf{可空}} &
      \multicolumn{1}{c}{\textbf{描述}} \\
      \endhead
      \multicolumn{4}{r}{{\tablename\ \thetable{} -- \emph{下一页续}}} \\
      \endfoot
      \endlastfoot
      <#assign columns = table.columns>
      <#list columns as column>
        [=column.latexName]  &  [=column.dataTypeDisplay]  & <#if column.nullable>是<#else>否</#if> & <#if column.comment??>[=column.latexComment]<#else>\todobox{TODO请补充此字段的描述}</#if> \\\hline
      </#list>
      \thickhline
    \end{longtable}
  \end{VerbatimOut}
  \LTXtable{\textwidth}{\jobname.vrb}


% vim: set ai nu nobk expandtab ts=4 sw=2 tw=72 syntax=tex :
