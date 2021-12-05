%!TEX TS-program = xelatex
%!TEX encoding = UTF-8 Unicode
%!TeX root = ../manual.tex

<#assign methods = interface.methods>
<#assign interfaceName = interface.name>
<#assign apiGroup = interface.group>
\subsection{[=interfaceName]接口}
  \label{subsec:subSec[=apiGroup]}
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

  \item{\bfseries 安全级别}
  <#if method.securityType??>
  [=method.securityType]
  <#else>
  \todobox{TODO: add method securityType here}
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
      \begin{tabular*}{\textwidth}{@{\extracolsep{0pt}}l|l|l|l}
      \thickhline
      \bfseries{参数} &
      \bfseries{类型} &
      \bfseries{自动注入} &
      \bfseries{说明} \\\hline

    <#list parameters as param>
      <#assign paramName = param.name>
      <#assign paramType = param.type>
      <#assign paramAutowired = param.autowired>
      [=paramName] & [=paramType] & [=paramAutowired?string('是','否')] & <#if param.needXref>类[=param.qualifiedType?keep_after_last(".")]的定义请参考表\ref{tab:mdl-[=param.qualifiedType]}<#else><#if param.description??>[=param.description]<#else>\todobox{TODO: add parameter description here}</#if></#if> \\\hline
    </#list>

      \thickhline
      \end{tabular*}
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
  <#if method.errorCodes??>
    此方法可能返回以下错误码: \\
    \begin{table}[H]
      \caption{\emph{[=methodName]方法错误码}}
      \label{tab:[=methodName]ErrCode}
      \begin{tabular*}{\textwidth}{@{\extracolsep{0pt}}l|l|l}
      \thickhline
      \bfseries{代码} &
      \bfseries{数字值} &
      \bfseries{说明} \\\hline
      <#list method.errorCodes as errorCode>
        <#if errorCode.name??>[=errorCode.latexName]<#else>无</#if> & <#if errorCode.number??>[=errorCode.number]<#else>无</#if> & <#if errorCode.description??>[=errorCode.description]<#else>无</#if> \\\hline
      </#list>
      \thickhline
      \end{tabular*}
    \end{table}
  <#else>该方法没有错误码</#if>

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
