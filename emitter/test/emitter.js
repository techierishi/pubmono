import {
  describe,
  it,
} from "https://deno.land/x/test_suite@0.9.0/mod.ts";
import {
  assertArrayIncludes,
  assertEquals
} from "https://deno.land/std@0.108.0/testing/asserts.ts";
import { Emitter } from 'https://deno.land/x/emitter@1.0.0/index.js';

function Custom() {
  Emitter.call(this)
}

Custom.prototype = Emitter.prototype;


describe('Custom', function(){
  describe('with Emitter.call(this)', function(){
    it('should work', function(){
      var emitter = new Custom;
      emitter.on('foo', () => console.log("Done"));
      emitter.emit('foo');
    })
  })
})


describe('Emitter', function(){
  describe('.on(event, fn)', function(){
    it('should add listeners', function(){
      var emitter = new Emitter();
      var calls = [];

      emitter.on('foo', function(val){
        calls.push('one', val);
      });

      emitter.on('foo', function(val){
        calls.push('two', val);
      });

      emitter.emit('foo', 1);
      emitter.emit('bar', 1);
      emitter.emit('foo', 2);

      assertArrayIncludes(calls,[ 'one', 1, 'two', 1, 'one', 2, 'two', 2 ]);
    })

    it('should add listeners for events which are same names with methods of Object.prototype', function(){
      var emitter = new Emitter();
      var calls = [];

      emitter.on('constructor', function(val){
        calls.push('one', val);
      });

      emitter.on('__proto__', function(val){
        calls.push('two', val);
      });

      emitter.emit('constructor', 1);
      emitter.emit('__proto__', 2);

      assertArrayIncludes(calls,[ 'one', 1, 'two', 2 ]);
    })
  })

  describe('.once(event, fn)', function(){
    it('should add a single-shot listener', function(){
      var emitter = new Emitter();
      var calls = [];

      emitter.once('foo', function(val){
        calls.push('one', val);
      });

      emitter.emit('foo', 1);
      emitter.emit('foo', 2);
      emitter.emit('foo', 3);
      emitter.emit('bar', 1);

      assertArrayIncludes(calls,[ 'one', 1 ]);
    })
  })

  describe('.off(event, fn)', function(){
    it('should remove a listener', function(){
      var emitter = new Emitter();
      var calls = [];

      function one() { calls.push('one'); }
      function two() { calls.push('two'); }

      emitter.on('foo', one);
      emitter.on('foo', two);
      emitter.off('foo', two);

      emitter.emit('foo');

      assertArrayIncludes(calls,[ 'one' ]);
    })

    it('should work with .once()', function(){
      var emitter = new Emitter();
      var calls = [];

      function one() { calls.push('one'); }

      emitter.once('foo', one);
      emitter.once('fee', one);
      emitter.off('foo', one);

      emitter.emit('foo');

      assertArrayIncludes(calls,[]);
    })

    it('should work when called from an event', function(){
      var emitter = new Emitter
        , called
      function b () {
        called = true;
      }
      emitter.on('tobi', function () {
        emitter.off('tobi', b);
      });
      emitter.on('tobi', b);
      emitter.emit('tobi');
      assertEquals(called, true);

      called = false;
      emitter.emit('tobi');

      assertEquals(called, false);
    });
  })

  describe('.off(event)', function(){
    it('should remove all listeners for an event', function(){
      var emitter = new Emitter();
      var calls = [];

      function one() { calls.push('one'); }
      function two() { calls.push('two'); }

      emitter.on('foo', one);
      emitter.on('foo', two);
      emitter.off('foo');

      emitter.emit('foo');
      emitter.emit('foo');

      assertArrayIncludes(calls,[]);
    })

    it('should remove event array to avoid memory leak', function() {
      var emitter = new Emitter();
      var calls = [];

      function cb() {}

      emitter.on('foo', cb);
      emitter.off('foo', cb);

      console.log('emitter._callbacks[NOT]', emitter._callbacks);

      assertEquals(emitter._callbacks['$foo'], undefined);

    })

    it('should only remove the event array when the last subscriber unsubscribes', function() {
      var emitter = new Emitter();
      var calls = [];

      function cb1() {}
      function cb2() {}

      emitter.on('foo', cb1);
      emitter.on('foo', cb2);
      emitter.off('foo', cb1);

      assertEquals(Object.keys(emitter._callbacks), ['$foo']);


    })
  })

  describe('.off()', function(){
    it('should remove all listeners', function(){
      var emitter = new Emitter();
      var calls = [];

      function one() { calls.push('one'); }
      function two() { calls.push('two'); }

      emitter.on('foo', one);
      emitter.on('bar', two);

      emitter.emit('foo');
      emitter.emit('bar');

      emitter.off();

      emitter.emit('foo');
      emitter.emit('bar');

      assertArrayIncludes(calls,['one', 'two']);
    })
  })

  describe('.listeners(event)', function(){
    describe('when handlers are present', function(){
      it('should return an array of callbacks', function(){
        var emitter = new Emitter();
        function foo(){}
        emitter.on('foo', foo);
        assertArrayIncludes(emitter.listeners('foo'), [foo])
      })
    })

    describe('when no handlers are present', function(){
      it('should return an empty array', function(){
        var emitter = new Emitter();
        assertArrayIncludes(emitter.listeners('foo'), [])
      })
    })
  })

  describe('.hasListeners(event)', function(){
    describe('when handlers are present', function(){
      it('should return true', function(){
        var emitter = new Emitter();
        emitter.on('foo', function(){});
        assertEquals(emitter.hasListeners('foo'), true);
      })
    })

    describe('when no handlers are present', function(){
      it('should return false', function(){
        var emitter = new Emitter();
        assertEquals(emitter.hasListeners('foo'), false);

      })
    })
  })
})



