#!/usr/bin/env -S deno run
import { readLines } from 'https://deno.land/std/io/buffer.ts'
import {
  decode as base64Decode
} from 'https://deno.land/std@0.82.0/encoding/base64.ts'


class DecodePubCert{

  async readPipe() {
    let str = ''
    for await (const l of readLines(Deno.stdin)) {
      str += l
    }
    return str
  }

  b64decode(str){
    const textDecoder = new TextDecoder('utf-8')
    const decodedValue = textDecoder.decode(base64Decode(str))
    return decodedValue
  }

  async run(){
    const pipedStr = await this.readPipe()
    const decodedStr = this.b64decode(pipedStr)
    const fileName = `${Math.random().toString(36).slice(2, 7)}.crt`
    await Deno.writeTextFile(fileName, decodedStr)
    const cmd = ["openssl", "x509", "-in", `${fileName}`,  "-text", "-noout"];
    const cmdRun = await Deno.run({
      cmd, 
      stdout: "piped",
      stderr: "piped"
    });
    const outStr = new TextDecoder().decode(await cmdRun.output())
    const errStr =  new TextDecoder().decode(await cmdRun.stderrOutput())

    if(outStr){
      console.log('Output:: ', outStr)
    }

    if(errStr){
      console.log('Error:: ', errStr);
    }

    cmdRun.close()
    await Deno.remove(fileName)
  }

}


await (new DecodePubCert().run())
