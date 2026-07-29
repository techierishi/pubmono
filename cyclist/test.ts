import { assertEquals, assertExists } from "https://deno.land/std@0.108.0/testing/asserts.ts";
import { Cyclist } from "https://deno.land/x/cyclist@v1.0.2/mod.ts"

Deno.test('basic put and get', () => {
  const list = new Cyclist(3)
  list.put(0, 'hello')
  list.put(1, 'world')
  assertEquals(list.get(0), 'hello')
  assertEquals(list.get(1), 'world')
})

Deno.test('overflow put and get', () => {
  const list = new Cyclist(2)
  list.put(0, 'hello')
  list.put(1, 'world')
  list.put(2, 'verden')
  assertEquals(list.get(0), 'verden')
  assertEquals(list.get(1), 'world')
  assertEquals(list.get(2), 'verden')
})

Deno.test('del', () => {
  const list = new Cyclist(2)
  list.put(0, 'hello')
  assertEquals(list.get(0), 'hello')
  list.del(0)
  assertExists(!list.get(0))
})

Deno.test('multiple of two', () => {
  const list = new Cyclist(3)
  assertEquals(list.size, 4)
})
