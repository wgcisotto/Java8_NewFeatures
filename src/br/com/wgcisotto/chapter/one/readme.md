**Capítulo 1**

_Java 8_

http://download.java.net/jdk8/docs/api/index.html

São praticamente 20 anos de Java desde o lançamento de sua primeira versão.
Apenas em 2004, com a chegada do Java 5, houve mudanças significativas na
linguagem. Em especial generics, enums e anotações.

Com a chegada do Java 8, em 2014, isso acontece mais uma vez. Novas possibili-
dades surgem com a entrada do lambda e dos method references, além de pequenas
mudanças na linguagem. A API de Collections, na qual as interfaces principais são
as mesmas desde 1998, recebe um significativo upgrade com a entrada dos Streams
e dos métodos default.


**1.1 Um balde de água morna?**

Se você está esperando algo tão poderoso quanto em Scala, Clojure e C#, certamente
sairá decepcionado. O legado e a idade do Java, além da ausência de value types e
reificação nos generics, impedem o uso de algumas estratégias. O time de desenvol-
vedores da linguagem tem também grande preocupação em deixar a sintaxe sempre
simples, evitando formas obscuras que trariam pequenos ganhos. Na nossa opinião,
faz bastante sentido.


**O que ficou de fora do Java 8?**

Para quebrar melhor a especificação do Java 8 em tarefas menores, foram criadas
as JEPs: JDK Enhancement Proposals. É uma ideia que nasceu dos PEPs, proposta
similar da comunidade Python. A JEP 0 é uma lista com todas essas propostas:

http://openjdk.java.net/jeps/0

Como você pode ver, são muitas as novidades no JDK8. Infelizmente nem todas
tiveram tempo suficiente para amadurecer. Entre as JEPs propostas, os Value Objects
ficaram de fora:

http://openjdk.java.net/jeps/169

Assim como o uso de literais para trabalhar com coleções:

http://openjdk.java.net/jeps/186

Entre outras ideias que ficaram de fora, temos diversas melhorias aos Garbage
Collectors já embutidos, assim como a possível reificação dos generics.
