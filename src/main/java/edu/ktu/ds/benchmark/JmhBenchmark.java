package edu.ktu.ds.benchmark;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

// Matuojamas vidutinis testo operacijų (arrayListGet() ir doublyLinkedListGet())
// vykdymo laikas. Plačiau žr. JMH pavyzdyje JMHSample_02_BenchmarkModes.java
@BenchmarkMode(Mode.AverageTime)
// Testo klasėje saugoma testo būsena (arrayList, doublyLinkedList ir indexes), kuri
// yra bendra visoms testą vykdančioms gijoms. Sudėtingesniems atvejams, kai
// testo metodai keičia testo būseną, t.y. ne tik skaito, bet ir rašo į ją,
// naudojamos atskiros būsenos klasės su įvairiais Scope (Scope.Benchmark arba
// Scope.Thread). Plačiau žr. JMH pavyzdžiuose JMHSample_03_States.java ir
// JMHSample_04_DefaultState.java 
@State(Scope.Benchmark)
// Išmatuotas laikas bus pateiktas mikrosekundėmis. Plačiau žr. JMH pavyzdyje
// JMHSample_02_BenchmarkModes.java 
@OutputTimeUnit(TimeUnit.MICROSECONDS)
// Testo pradžioje atliekamas virtualios mašinos "apšildymas" (@Warmup), po kurio
// testo metodų vykdymo laikas jau matuojamas (@Measurement). Abiem atvejais
// (tiek "apšildymo", tiek matavimų) testas kartojamas keletą kartų, arba
// iteracijų (pagal nutylėjimą atliekamos 5 iteracijos). Kiekvienos iteracijos
// metu testo metodai pakartotinai vykdomi nurodytą laiko tarpą (pvz. 1 sekundę,
// kaip nurodyta time ir timeUnit parametrais). Plačiau žr. JMH pavyzdyje
// JMHSample_20_Annotations.java 
@Warmup(time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(time = 1, timeUnit = TimeUnit.SECONDS)
public class JmhBenchmark {

    static final int OPERATION_COUNT = 1_000;

    // Parametrai leidžia testą atlikti su skirtingomis konfigūracijomis. Testas
    // bus įvykdytas keletą kartų, kiekvieną sykį listSize užpildant viena iš
    // @Param anotacijoje pateiktų reikšmių. Plačiau žr. JMH pavyzdyje
    // JMHSample_27_Params.java
    @Param({ "64000", "4000", "8000", "16000", "32000"})
    public int listSize;

    ArrayList<Float> arrayList = new ArrayList<>();
    DoublyLinkedList<Float> doublylinkedList = new DoublyLinkedList<>();
    int[] indexes = new int[OPERATION_COUNT];

    // Testo būsenos valdymo metodai žymimi @Setup (vykdoma prieš testą) arba
    // @TearDown (vykdoma po testo) anotacijomis. Šių metodų vykdymo laikas
    // nematuojamas. Level parametras nurodo, kada metodai bus vykdomi:
    //   Level.Trial - prieš/po visą testą, t.y. testo iteracijų seką
    //   Level.Iteration - prieš/po kiekvieną iteraciją, t.y. testo metodų iškvietimo seką
    //   Level.Invocation - prieš/po kiekvieną testo metodo iškvietimą
    // Plačiau žr. JMH pavyzdžiuose JMHSample_05_StateFixtures.java ir
    // JMHSample_06_FixtureLevel.java
    @Setup(Level.Trial)
    public void generateLists() {
        Util.generateList(arrayList, listSize);
        Util.generateList2(doublylinkedList, listSize);
    }

    @Setup(Level.Iteration)
    public void generateIndexes() {
        Util.generateIndexes(indexes, listSize);
    }

    // @Benchmark anotacija žymi metodus, kurių vykdymo laikas yra matuojamas.
    // JMH pagal anotacijas sugeneruoja pagalbinį kodą, leidžianti greitaveikos
    // matavimus atlikti kuo patikimiau. Plačiau žr. JMH pavyzdyje
    // JMHSample_01_HelloWorld.java
    @Benchmark
    public void arrayListGet() {
        listGet(arrayList);
    }

    @Benchmark
    public void doublyLinkedListGet() {
        listGet2(doublylinkedList);
    }

    private void listGet(List<Float> list) {
        for (int i = list.size()-1; i >= 0; i--) {
             list.get(i);
        }
       /* for (int i = 0; i < list.size(); i++) {
            list.get(i);
        }*/
    }
    
    private void listGet2(DoublyLinkedList<Float> list) {
       /* for (int i = 0; i < list.Size(); i++) {
            list.nextRigth();
        }*/
        for (int i = list.Size()-1; i >= 0; i--) {
              list.nextLeft2();
        }
    }

    // Kodas, kurio rezultatai nepanaudojami, optimizavimo metu gali būti
    // pašalintas (pvz. metodo List.get(int) iškvietimas). Siekiant to išvengti,
    // galima rezultatus perduoti juos "panaudojantiems" Blackhole objektams.
    // Plačiau žr. JMH pavyzdžiuose JMHSample_08_DeadCode.java ir 
    // JMHSample_09_Blackholes.java
    @Benchmark
    public void arrayListGetAndConsume(Blackhole bh) {
        listGetAndConsume(arrayList, bh);
    }

    @Benchmark
    public void doublylinkedListGetAndConsume(Blackhole bh) {
        listGetAndConsume2(doublylinkedList, bh);
    }
    
    private void listGetAndConsume(List<Float> list, Blackhole bh) {
        /*  for (int i = 0; i < list.size(); i++) {
            bh.consume(list.get(i));
        }*/
           for (int i = list.size()-1; i >= 0; i--) {
              bh.consume(list.get(i));
        }
    }
    
    private void listGetAndConsume2(DoublyLinkedList<Float> list, Blackhole bh) {
       /*  for (int i = 0; i < list.Size(); i++) {
            bh.consume(list.nextRigth2());
        }*/
        for (int i = list.Size()-1; i >= 0; i--) {
              bh.consume(list.nextLeft());
        }
    }

    // Rekomenduojamas JMH testų paleidimo būdas, leidžiantis išvengti Java IDE
    // įtakos testo rezultatams - naudoti testo jar failą:
    //   > java -jar target/benchmarks.jar
    // Tačiau laboratorinių darbų metu testų vykdymui patogiau naudoti JMH
    // Runner ir tiesiog įvykdyti testo klasę.
    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(JmhBenchmark.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(opt).run();
    }
}
