%!TEX TS-program = xelatex
%!TEX encoding = UTF-8 Unicode
%!TeX root = ../manual.tex

<#assign methods = interface.methods>
<#assign interfaceName = interface.className>
\subsection{[=interfaceName]接口}
  \label{subsec:subSec[=interfaceName]}
<#list methods as method>
    <#assign methodName = method.name>
    %%%%%%%% start of  [=methodName] documentation %%%%%%%%
    \subsubsection{[=methodName]方法}
    \label{subsec:subSubSec[=interfaceName][=methodName]}

\begin{description}
  \item{\bfseries 概述}
  <#if method.description??>
  [=method.latexDescription]
  <#else>
  \todobox{TODO: add method description here}
  </#if>

  \item{\bfseries 方法签名}
    <#assign methodSig = method.signature>
    \begin{listing}[H]
      \caption{\emph{[=methodName]方法签名}}
      \label{lst:signature[=methodName]}
      \begin{javacode}
[=methodSig]
      \end{javacode}
    \end{listing}

  \item{\bfseries 入参}

  <#if method.parameters??>
    <#assign parameters = method.parameters>
    该方法的参数如下表所示：

    \begin{table}[H]
      \caption{\emph{[=methodName]方法参数表}}
      \label{tab:[=methodName]Params}
      \begin{tabularx}{\textwidth}{l|l|X}
      \thickhline
      \bfseries{参数} &
      \bfseries{类型} &
      \bfseries{说明} \\\hline

    <#list parameters as param>
      <#assign paramName = param.name>
      <#assign paramType = param.type>
      [=paramName] & [=paramType] & <#if param.needXref>类[=param.qualifiedType?keep_after_last(".")]的定义请参考表\ref{tab:mdl-[=param.qualifiedType]}</#if> \\\hline
    </#list>

      \thickhline
      \end{tabularx}
    \end{table}
  <#else>该方法没有参数</#if>

  \item{\bfseries 返回值}

  <#assign returnType = method.returnType>
    <#if returnType??>
      <#assign returnTypeName = returnType.type>
      该方法返回值类型为[=returnTypeName]。
      <#if returnType.needXref>类[=returnType.qualifiedType?keep_after_last(".")]的定义请参考表\ref{tab:mdl-[=returnType.qualifiedType]}</#if>
    <#else>
    该方法没有返回值
    </#if>

  \item{\bfseries 错误码}
    此方法可能返回以下错误码: \\
    \todobox{TODO: list error name here}
    \begin{table}[H]
      \caption{\emph{[=methodName]方法错误码}}
      \label{tab:[=methodName]ErrCode}
      \begin{tabular*}{\textwidth}{@{\extracolsep{0pt}}l|l|l}
      \thickhline
      \bfseries{代码} &
      \bfseries{数字值} &
      \bfseries{说明} \\\hline
      YOUR\_ERROR & 11000 & 参数错误 \\
      \thickhline
      \end{tabular*}
    \end{table}

  \item{\bfseries 示例代码}
    \begin{listing}[H]
      \caption{\emph{[=methodName]方法示例代码}}
      \label{lst:sample[=methodName]}
      \begin{javacode}
          // TODO: include sample name to call your method here
      \end{javacode}
    \end{listing}

\end{description}
    %%%%%%%% end of  [=methodName] documentation %%%%%%%%

</#list>

% vim: set ai nu nobk expandtab ts=4 sw=2 tw=72 syntax=tex :
