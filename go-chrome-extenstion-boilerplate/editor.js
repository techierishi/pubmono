function init() {
    const importObj = {
        module: {} 
     };
     const go = new Go(); 
     async function fetchAndInstantiate() { 
        const response = await fetch("simple.wasm"); 
        const buffer = await response.arrayBuffer(); 
        const obj = await WebAssembly.instantiate(buffer, go.importObject); 
        console.log(obj); 
        go.run(obj.instance); 
     } 
     fetchAndInstantiate(); 

}

document.addEventListener('DOMContentLoaded', () => {
    init()
}, false)
