package br.com.book.sample;

import br.com.book.sample.model.Customer;
import br.com.book.sample.model.Payment;
import br.com.book.sample.model.Product;
import br.com.book.sample.model.Subscription;

import java.math.BigDecimal;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Capitulo11 {

    public static void main (String[] args){

//      Massa para testes.
//      Criado 4 usuarios

        Customer william = new Customer("William Cisotto");

        Customer carol = new Customer("Ana Carolina Cisotto");

        Customer ian = new Customer("Ian Cisotto");

        Customer laura = new Customer("Laura Cisotto");

//      Criado 6 produtos

        Product bach = new Product("Bach Completo",
                Paths.get("/music/bach.mp3"), new BigDecimal(100));

        Product poderosas = new Product("Poderosas Anita",

                Paths.get("/music/poderosas.mp3"), new BigDecimal(90));

        Product bandeira = new Product("Bandeira Brasil",
                Paths.get("/images/brasil.jpg"), new BigDecimal(50));

        Product beauty = new Product("Beleza Americana",
                Paths.get("beauty.mov"), new BigDecimal(150));

        Product vingadores = new Product("Os Vingadores",
                Paths.get("/movies/vingadores.mov"), new BigDecimal(200));

        Product amelie = new Product("Amelie Poulain",
                Paths.get("/movies/amelie.mov"), new BigDecimal(100));


        // Today
        LocalDateTime today = LocalDateTime.now();

        // Yesterday
        LocalDateTime yesterday = today.minusDays(1);

        //lastMonth
        LocalDateTime lastMonth = today.minusMonths(1);


        Payment payment1 = new Payment(Arrays.asList(bach, poderosas), today, ian);

        Payment payment2 = new Payment(Arrays.asList(bach, bandeira, amelie), yesterday, carol);

        Payment payment3 = new Payment(Arrays.asList(beauty, vingadores, bach), today, laura);

        Payment payment4 = new Payment(Arrays.asList(bach, poderosas, amelie), lastMonth, william);

        Payment payment5 = new Payment(Arrays.asList(beauty, amelie), yesterday, ian);

        List<Payment> payments = Arrays.asList(payment1, payment2, payment3, payment4, payment5);


//      Primeiro desafio ordenar os pagamentos por data e imprimi-los.
//      Para isso Utilizaremos o sorted e o forEach do stream.

        payments.stream()
                .sorted(Comparator.comparing(Payment::getDate))
                .forEach(System.out::println);

//      Segundo desafio, Reduzinho o BigDecimal em somas

        payment1.getProducts().stream()
                .map(Product::getPrice)
                .reduce(BigDecimal::add)
                .ifPresent(System.out::println);

        BigDecimal total =
                payment1.getProducts().stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);


        System.out.println(total);

        Stream<BigDecimal> pricesStream =
            payments.stream()
            .map(payment -> payment.getProducts().stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add));

        BigDecimal total2 =
                payments.stream()
                .map(p -> p.getProducts().stream()
                            .map(Product::getPrice)
                            .reduce(BigDecimal.ZERO, BigDecimal::add))
                .reduce(BigDecimal.ZERO,BigDecimal::add);

        System.out.println(total2);


//      Criamos uma function para evitar escrever muito cod como o exemplo acima.

        Function<Payment, Stream<BigDecimal>> mapper =
                p -> p.getProducts().stream().map(Product::getPrice);


//      Sem utlizarmos o reduce temos uma Stream de cada price de cada product

        Stream<BigDecimal> priceOfEachProduct =
                payments.stream()
                .flatMap(mapper);

        System.out.println(priceOfEachProduct);
        priceOfEachProduct.forEach(System.out::println);


//     Usando Reduce.


        BigDecimal totalFlat =
                payments.stream()
                        .flatMap(p -> p.getProducts().stream().map(Product::getPrice))
                        .reduce(BigDecimal.ZERO,BigDecimal::add);

        System.out.println(totalFlat);



//     Descobrindo qual é o product mais vendido.



        Stream<Product> products = payments.stream()
                .map(Payment::getProducts)
                .flatMap(p -> p.stream());

        // em vez de p -> p.stream(), ha a possibilidade de passar o lambda como method reference: List::stream:


        Stream<Product> products1 = payments.stream()
                .map(Payment::getProducts)
                .flatMap(List::stream);

        // sempre podemos juntar dois maps em um unico map

        Stream<Product> products2 = payments.stream()
                .flatMap(p -> p.getProducts().stream());


        Map<Product, Long> topProducts = payments.stream()
                .flatMap(p -> p.getProducts().stream())
                .collect(Collectors.groupingBy(Function.identity(),
                        Collectors.counting()));

        System.out.println(topProducts);


        topProducts.entrySet().stream()
                .forEach(System.out::println);

        topProducts.entrySet().stream()
                .max(Comparator.comparing(Map.Entry::getValue))
                .ifPresent(System.out::println);


        // Como calcular a soma toda total por produto
        // em vez de agrupar o valor com Collectors.counting queremos fazer algo como Collectors.summing.
        // Para realizarmos a soma em BigDecimal teremos de deixar o reduce explícito


        Map<Product, BigDecimal> totalValuePerProduct = payments.stream()
                .flatMap(p -> p.getProducts().stream())
                .collect(Collectors.groupingBy(Function.identity(),
                        Collectors.reducing(BigDecimal.ZERO, Product::getPrice, BigDecimal::add)));

        // ordenando a saida.

        totalValuePerProduct.entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getValue))
                .forEach(System.out::println);

        // Quais são ps produtos de cada Cliente?
        // em um primeiro momento, podemos ter, para cada Custumer, sua List<Payment>, bastando agrupar os payments com
        // groupingBy(Payment::getCustomer)

        Map<Customer, List<Payment>> customerToPayments =
                payments.stream()
                .collect(Collectors.groupingBy(Payment::getCustomer));

        System.out.println(customerToPayments);


        // Não estamos interessados nos pauments de um Customer, e sim nas lista de Product dentro de cada um desses Payments.
        // uma implementação incocente vai derar uma List<List<Product>> dentro do valor do map

        Map<Customer, List<List<Product>>> customerToProductList =
                payments.stream()
                .collect(Collectors.groupingBy(Payment::getCustomer,
                        Collectors.mapping(Payment::getProducts, Collectors.toList())));


        System.out.println(customerToProductList);


        customerToProductList.entrySet().stream()
                .sorted(Comparator.comparing(e -> e.getKey().getName()))
                .forEach(System.out::println);

        Map<Customer, List<Product>> customerToProducts2streps =
                customerToProductList.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        e -> e.getValue().stream()
                        .flatMap(List::stream)
                        .collect(Collectors.toList())));

        customerToProducts2streps.entrySet().stream()
                .sorted(Comparator.comparing(e -> e.getKey().getName()))
                .forEach(System.out::println);

        // Usamos o Collectors.toMap para criar um novo map no qual a chave continua a mesma (Map.Entry::getKey)

        // Poderiamos ter feito tudo com uma unica chamada. Creio que nesse caso estouramos o limite de uso dessa API

        Map<Customer, List<Product>> customerToProduct1step = payments.stream()
                .collect(Collectors.groupingBy(Payment::getCustomer,
                        Collectors.mapping(Payment::getProducts, Collectors.toList())))
                        .entrySet().stream()
                            .collect(Collectors.toMap(Map.Entry::getKey,
                                    e-> e.getValue().stream()
                                        .flatMap(List::stream)
                                        .collect(Collectors.toList())));

        // Dificil de seguir a sequencia, certamente quebrar em alguns pedacoes é o mais aconselhavel

        //Como sempre ha outras formas de resolver o mesmo problema.a

        Map<Customer, List<Product>> customerToProducts = payments.stream()
                .collect(Collectors.groupingBy(Payment::getCustomer,
                        Collectors.reducing(Collections.emptyList(),
                                Payment::getProducts,
                                (l1, l2) -> { List<Product> l = new ArrayList<>();
                                            l.addAll(l1);
                                            l.addAll(l2);
                                            return l;} )));

        // O resultado é exatamente o mesmo que com o flatmap

        // Qual o nosso cliente mais Especial ?
        // Qual seria a estrategia para conseguir o desejado Map<Customer, BigDeciaml> ?

        Map<Customer, BigDecimal> totalValuePerCustomer = payments.stream()
                .collect(Collectors.groupingBy(Payment::getCustomer,
                        Collectors.reducing(BigDecimal.ZERO,
                                p -> p.getProducts().stream().map(Product::getPrice).reduce(
                                        BigDecimal.ZERO, BigDecimal::add),
                                BigDecimal::add)));

        System.out.println(totalValuePerCustomer);

        // O código esta no minimo muito acumulado.

        // Vamos quebrar essa reducao, criando uma variavel temporaria responsavel por mapear um payment para soma de todos os precos
        // de serus produtos

        Function<Payment, BigDecimal> paymentToTotal =
                p -> p.getProducts().stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // com isso podemos usar essa function no reducing

        Map<Customer, BigDecimal> totalValuePerCustomer2 = payments.stream()
                .collect(Collectors.groupingBy(Payment::getCustomer,
                        Collectors.reducing(BigDecimal.ZERO,
                                paymentToTotal,
                                BigDecimal::add)));

        totalValuePerCustomer2.entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getValue))
                .forEach(System.out::println);


        // Relatorio com datas.

//        É muito simples separarmos os pagamentos por data, usando um groupingby(Payment::getDate). Há um perigo nisso:
//        LocalDateTIME vai agrupar os pagamentos ate pelos milissegundos. Não é o que queremos.
//        Podemos agrupar por LocalDate, usando um groupingBy(p -> p.getDate().toLocalDate()), ou em um intevalo ainda maior,
//        como por ano e mes. Para isso usamos o YearMonth

        Map<YearMonth, List<Payment>> paymentsPerMonth = payments.stream()
                .collect(Collectors.groupingBy(p -> YearMonth.from(p.getDate())));

        paymentsPerMonth.entrySet().stream()
                .forEach(System.out::println);


        // E se quisermos saber, tambem por mesl, quanto foi faturado na loja ? basta agrupar com o mesmo criterio
        // e usar a reducao: somando todos os preceos de todos os produtos de todos os pagamentos

        Map<YearMonth, BigDecimal> paymentsValuePerMonth = payments.stream()
                .collect(Collectors.groupingBy(p -> YearMonth.from(p.getDate()),
                        Collectors.reducing(BigDecimal.ZERO,
                                p -> p.getProducts().stream()
                        .map(Product::getPrice)
                        .reduce(BigDecimal.ZERO, BigDecimal::add),
                                BigDecimal::add)));

        paymentsValuePerMonth.entrySet().stream()
                .forEach(System.out::println);


        BigDecimal monthlyFee = new BigDecimal("99.90");

        Subscription s1 = new Subscription(monthlyFee,
                yesterday.minusMonths(5), ian);

        Subscription s2 = new Subscription(monthlyFee,
                yesterday.minusMonths(8), today.minusMonths(1), carol);

        Subscription s3 = new Subscription(monthlyFee,
                yesterday.minusMonths(5), today.minusMonths(2), william);

        List<Subscription> subscriptions  = Arrays.asList(s1, s2, s3);

        // Como calcular quantos meses foram pagos atraves daquela assinatura?

        long meses = ChronoUnit.MONTHS.between(s1.getBegin(), LocalDateTime.now());

        System.out.println("Meses : " +  meses);

        // e se a assinatura terminou ? em vez de enchermos nosso codigo cm ifs, tiramos proveito do optional.

        long meses2 = ChronoUnit.MONTHS.between(s1.getBegin(), s2.getEnd().orElse(LocalDateTime.now()));

        System.out.println("Meses c Optional: " +  meses2);

        // para calcular o valor gerado por aquela assinatura basta multiplicar esse numero de meses pelo custo mensal.

        BigDecimal totalAssinatura1 = s1.getMonthlyFee()
                                .multiply(new BigDecimal(ChronoUnit.MONTHS
                                                .between(s1.getBegin(), s1.getEnd().orElse(LocalDateTime.now()))));

        System.out.println("Total Assinatura 1 " + totalAssinatura1);


        // agora criado o metodo na propria assinatura
        // podemos calcular o total de todas as assinatura.

        BigDecimal totalAssinatura = subscriptions.stream()
                                        .map(Subscription::getTotalPaid)
                                        .reduce(BigDecimal.ZERO, BigDecimal::add);

        System.out.println("Total todas as assinaturas: " + totalAssinatura);

    }

}
