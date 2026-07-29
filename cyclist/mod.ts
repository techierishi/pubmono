const twoify = function (n: any) {
  if (n && !(n & (n - 1))) return n
  let p = 1
  while (p < n) p <<= 1
  return p
}

export class Cyclist {

  mask: number = 0;
  size: number = 0;
  values: Array<any> = [];
  constructor (size: number) {
    if (!(this instanceof Cyclist)) return new Cyclist(size)
    size = twoify(size)
    this.mask = size - 1
    this.size = size
    this.values = new Array(size)
  }
  
  put(index: number, val: any) {
    var pos = index & this.mask
    this.values[pos] = val
    return pos
  }
  
  get(index: number) {
    return this.values[index & this.mask]
  }
  
  del(index: number) {
    var pos = index & this.mask
    var val = this.values[pos]
    this.values[pos] = undefined
    return val
  }
}