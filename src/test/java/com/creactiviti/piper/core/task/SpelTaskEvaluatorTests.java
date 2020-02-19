
package com.creactiviti.piper.core.task;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.creactiviti.piper.core.MapObject;
import com.creactiviti.piper.core.context.MapContext;
import com.google.common.collect.ImmutableMap;

public class SpelTaskEvaluatorTests {

  @Test
  public void test1 () {
    SpelTaskEvaluator evaluator = new SpelTaskEvaluator();
    TaskExecution jt = SimpleTaskExecution.create();
    TaskExecution evaluated = evaluator.evaluate(jt, new MapContext(Collections.emptyMap()));
    Assertions.assertEquals(evaluated.asMap(),jt.asMap());
  }

  @Test
  public void test2 () {
    SpelTaskEvaluator evaluator = new SpelTaskEvaluator();
    TaskExecution jt = SimpleTaskExecution.createFrom("hello", "world");
    TaskExecution evaluated = evaluator.evaluate(jt, new MapContext(Collections.emptyMap()));
    Assertions.assertEquals(evaluated.asMap(),jt.asMap());
  }

  @Test
  public void test3 () {
    SpelTaskEvaluator evaluator = new SpelTaskEvaluator();
    TaskExecution jt = SimpleTaskExecution.createFrom("hello", "${name}");
    TaskExecution evaluated = evaluator.evaluate(jt, new MapContext(Collections.singletonMap("name", "arik")));
    Assertions.assertEquals("arik",evaluated.getString("hello"));
  }

  @Test
  public void test4 () {
    SpelTaskEvaluator evaluator = new SpelTaskEvaluator();
    TaskExecution jt = SimpleTaskExecution.createFrom("hello", "${firstName} ${lastName}");
    MapContext ctx = new MapContext();
    ctx.put("firstName", "Arik");
    ctx.put("lastName", "Cohen");
    TaskExecution evaluated = evaluator.evaluate(jt, ctx);
    Assertions.assertEquals("Arik Cohen",evaluated.getString("hello"));
  }

  @Test
  public void test5 () {
    SpelTaskEvaluator evaluator = new SpelTaskEvaluator();
    TaskExecution jt = SimpleTaskExecution.createFrom("hello", "${T(java.lang.Integer).valueOf(number)}");
    MapContext ctx = new MapContext();
    ctx.put("number", "5");
    TaskExecution evaluated = evaluator.evaluate(jt, ctx);
    Assertions.assertEquals(Integer.valueOf(5),((Integer)evaluated.get("hello")));
  }

  @Test
  public void test6 () {
    SpelTaskEvaluator evaluator = new SpelTaskEvaluator();
    TaskExecution jt = SimpleTaskExecution.createFrom("list", Arrays.asList("${firstName}","${lastName}"));
    MapContext ctx = new MapContext();
    ctx.put("firstName", "Arik");
    ctx.put("lastName", "Cohen");
    TaskExecution evaluated = evaluator.evaluate(jt, ctx);
    Assertions.assertEquals(Arrays.asList("Arik","Cohen"),evaluated.getList("list", String.class));
  }

  @Test
  public void test7 () {
    SpelTaskEvaluator evaluator = new SpelTaskEvaluator();
    TaskExecution jt = SimpleTaskExecution.createFrom("map", Collections.singletonMap("hello", "${firstName}"));
    MapContext ctx = new MapContext();
    ctx.put("firstName", "Arik");
    TaskExecution evaluated = evaluator.evaluate(jt, ctx);
    Assertions.assertEquals(MapObject.of(Collections.singletonMap("hello", "Arik")),evaluated.getMap("map"));
  }

  @Test
  public void test8 () {
    SpelTaskEvaluator evaluator = new SpelTaskEvaluator();
    TaskExecution jt = SimpleTaskExecution.createFrom("mult","${n1*n2}");
    MapContext ctx = new MapContext();
    ctx.put("n1", 5);
    ctx.put("n2", 3);
    TaskExecution evaluated = evaluator.evaluate(jt, ctx);
    Assertions.assertEquals(Integer.valueOf(15),evaluated.getInteger("mult"));
  }

  @Test
  public void test9 () {
    SpelTaskEvaluator evaluator = new SpelTaskEvaluator();
    TaskExecution jt = SimpleTaskExecution.createFrom("message", "${name}");
    TaskExecution evaluated = evaluator.evaluate(jt, new MapContext(Collections.emptyMap()));
    Assertions.assertEquals("${name}",evaluated.getString("message"));
  }

  @Test
  public void test10 () {
    SpelTaskEvaluator evaluator = new SpelTaskEvaluator();
    TaskExecution jt = SimpleTaskExecution.createFrom("message", "yo ${name}");
    TaskExecution evaluated = evaluator.evaluate(jt, new MapContext(Collections.emptyMap()));
    Assertions.assertEquals("yo ${name}",evaluated.getString("message"));
  }

  @Test
  public void test11 () {
    SpelTaskEvaluator evaluator = new SpelTaskEvaluator();
    TaskExecution jt = SimpleTaskExecution.createFrom("thing", "${number}");
    TaskExecution evaluated = evaluator.evaluate(jt, new MapContext(Collections.singletonMap("number", 1)));
    Assertions.assertEquals(Integer.valueOf(1),evaluated.get("thing"));
  }

  @Test
  public void test12 () {
    SpelTaskEvaluator evaluator = new SpelTaskEvaluator();
    TaskExecution jt = SimpleTaskExecution.createFrom("thing", "${number*3}");
    TaskExecution evaluated = evaluator.evaluate(jt, new MapContext(Collections.singletonMap("number", 1)));
    Assertions.assertEquals(Integer.valueOf(3),evaluated.get("thing"));
  }

  @Test
  public void test13 () {
    SpelTaskEvaluator evaluator = new SpelTaskEvaluator();
    TaskExecution jt = SimpleTaskExecution.createFrom("thing", "${number*3}");
    TaskExecution evaluated = evaluator.evaluate(jt, new MapContext(Collections.emptyMap()));
    Assertions.assertEquals("${number*3}",evaluated.get("thing"));
  }

  @Test
  public void test14 () {
    SpelTaskEvaluator evaluator = new SpelTaskEvaluator();
    TaskExecution jt = SimpleTaskExecution.createFrom("list", "${range(1,3)}");
    TaskExecution evaluated = evaluator.evaluate(jt, new MapContext(Collections.emptyMap()));
    Assertions.assertEquals(Arrays.asList(1,2,3),evaluated.get("list"));
  }

  @Test
  public void test15 () {
    SpelTaskEvaluator evaluator = new SpelTaskEvaluator();
    TaskExecution jt = SimpleTaskExecution.createFrom("sub",Collections.singletonMap("list", "${range(1,3)}"));
    TaskExecution evaluated = evaluator.evaluate(jt, new MapContext(Collections.emptyMap()));
    Assertions.assertEquals(Arrays.asList(1,2,3),evaluated.getMap("sub").get("list"));
  }

  @Test
  public void test16 () {
    SpelTaskEvaluator evaluator = new SpelTaskEvaluator();
    TaskExecution jt = SimpleTaskExecution.createFrom("message", "${item1}-${item2}");
    TaskExecution evaluated = evaluator.evaluate(jt, new MapContext(ImmutableMap.of("item1", "hello","item2","world")));
    Assertions.assertEquals("hello-world",evaluated.get("message"));
  }

  @Test
  public void test17 () {
    SpelTaskEvaluator evaluator = new SpelTaskEvaluator();
    TaskExecution jt = SimpleTaskExecution.createFrom("someBoolean", "${boolean('1')}");
    TaskExecution evaluated = evaluator.evaluate(jt, new MapContext(Collections.emptyMap()));
    Assertions.assertEquals(Boolean.valueOf(true),evaluated.get("someBoolean"));
  }

  @Test
  public void test18 () {
    SpelTaskEvaluator evaluator = new SpelTaskEvaluator();
    TaskExecution jt = SimpleTaskExecution.createFrom("someByte", "${byte('127')}");
    TaskExecution evaluated = evaluator.evaluate(jt, new MapContext(Collections.emptyMap()));
    Assertions.assertEquals(Byte.valueOf(Byte.MAX_VALUE),evaluated.get("someByte"));
  }

  @Test
  public void test19 () {
    SpelTaskEvaluator evaluator = new SpelTaskEvaluator();
    TaskExecution jt = SimpleTaskExecution.createFrom("someChar", "${char('c')}");
    TaskExecution evaluated = evaluator.evaluate(jt, new MapContext(Collections.emptyMap()));
    Assertions.assertEquals(Character.valueOf('c'),evaluated.get("someChar"));
  }

  @Test
  public void test20 () {
    SpelTaskEvaluator evaluator = new SpelTaskEvaluator();
    TaskExecution jt = SimpleTaskExecution.createFrom("someShort", "${short('32767')}");
    TaskExecution evaluated = evaluator.evaluate(jt, new MapContext(Collections.emptyMap()));
    Assertions.assertEquals(Short.valueOf(Short.MAX_VALUE),evaluated.get("someShort"));
  }

  @Test
  public void test21 () {
    SpelTaskEvaluator evaluator = new SpelTaskEvaluator();
    TaskExecution jt = SimpleTaskExecution.createFrom("someInt", "${int('1')}");
    TaskExecution evaluated = evaluator.evaluate(jt, new MapContext(Collections.emptyMap()));
    Assertions.assertEquals(Integer.valueOf(1),evaluated.get("someInt"));
  }

  @Test
  public void test22 () {
    SpelTaskEvaluator evaluator = new SpelTaskEvaluator();
    TaskExecution jt = SimpleTaskExecution.createFrom("someLong", "${long('1')}");
    TaskExecution evaluated = evaluator.evaluate(jt, new MapContext(Collections.emptyMap()));
    Assertions.assertEquals(Long.valueOf(1L),evaluated.get("someLong"));
  }

  @Test
  public void test23 () {
    SpelTaskEvaluator evaluator = new SpelTaskEvaluator();
    TaskExecution jt = SimpleTaskExecution.createFrom("someFloat", "${float('1.337')}");
    TaskExecution evaluated = evaluator.evaluate(jt, new MapContext(Collections.emptyMap()));
    Assertions.assertEquals(Float.valueOf(1.337f),evaluated.get("someFloat"));
  }

  @Test
  public void test24 () {
    SpelTaskEvaluator evaluator = new SpelTaskEvaluator();
    TaskExecution jt = SimpleTaskExecution.createFrom("someDouble", "${double('1.337')}");
    TaskExecution evaluated = evaluator.evaluate(jt, new MapContext(Collections.emptyMap()));
    Assertions.assertEquals(Double.valueOf(1.337d),evaluated.get("someDouble"));
  }

  @Test
  public void test25 () {
    SpelTaskEvaluator evaluator = new SpelTaskEvaluator();
    TaskExecution jt = SimpleTaskExecution.createFrom("joined", "${join(',',range(1,3))}");
    TaskExecution evaluated = evaluator.evaluate(jt, new MapContext(Collections.emptyMap()));
    Assertions.assertEquals("1,2,3",evaluated.get("joined"));
  }

  @Test
  public void test26 () {
    SpelTaskEvaluator evaluator = new SpelTaskEvaluator();
    TaskExecution jt = SimpleTaskExecution.createFrom("joined", "${join(',',range(1,1))}");
    TaskExecution evaluated = evaluator.evaluate(jt, new MapContext(Collections.emptyMap()));
    Assertions.assertEquals("1",evaluated.get("joined"));
  }

  @Test
  public void test27 () {
    SpelTaskEvaluator evaluator = new SpelTaskEvaluator();
    TaskExecution jt = SimpleTaskExecution.createFrom("joined", "${join(' and ',{'a','b','c'})}");
    TaskExecution evaluated = evaluator.evaluate(jt, new MapContext(Collections.emptyMap()));
    Assertions.assertEquals("a and b and c",evaluated.get("joined"));
  }

  @Test
  public void test28 () {
    SpelTaskEvaluator evaluator = new SpelTaskEvaluator();
    TaskExecution jt = SimpleTaskExecution.createFrom("concatenated", "${concat({'a','b','c'}, {'d','e','f'})}");
    TaskExecution evaluated = evaluator.evaluate(jt, new MapContext(Collections.emptyMap()));
    Assertions.assertEquals(Arrays.asList("a","b","c","d","e","f"),evaluated.get("concatenated"));
  }

  @Test
  public void test29 () {
    SpelTaskEvaluator evaluator = new SpelTaskEvaluator();
    TaskExecution jt = SimpleTaskExecution.createFrom("concatenated", "${concat({'a','b','c'}, range(1,3))}");
    TaskExecution evaluated = evaluator.evaluate(jt, new MapContext(Collections.emptyMap()));
    Assertions.assertEquals(Arrays.asList("a","b","c",1,2,3),evaluated.get("concatenated"));
  }

  @Test
  public void test30 () {
    SpelTaskEvaluator evaluator = new SpelTaskEvaluator();
    TaskExecution jt = SimpleTaskExecution.createFrom("flattened", "${flatten({{'a','b','c'},{'d','e','f'}})}");
    TaskExecution evaluated = evaluator.evaluate(jt, new MapContext(Collections.emptyMap()));
    Assertions.assertEquals(Arrays.asList("a","b","c","d","e","f"),evaluated.get("flattened"));
  }

  @Test
  public void test31 () {
    SpelTaskEvaluator evaluator = new SpelTaskEvaluator();
    TaskExecution jt = SimpleTaskExecution.createFrom("flattened", "${flatten({{'a','b','c'},range(1,3)})}");
    TaskExecution evaluated = evaluator.evaluate(jt, new MapContext(Collections.emptyMap()));
    Assertions.assertEquals(Arrays.asList("a","b","c",1,2,3),evaluated.get("flattened"));
  }
  
  @Test
  public void test32 () {
    SpelTaskEvaluator evaluator = new SpelTaskEvaluator();
    TaskExecution jt = SimpleTaskExecution.createFrom("tempDir", "${tempDir()}");
    TaskExecution evaluated = evaluator.evaluate(jt, new MapContext(Collections.emptyMap()));
    Assertions.assertEquals(System.getProperty("java.io.tmpdir"),evaluated.get("tempDir"));
  }
}
