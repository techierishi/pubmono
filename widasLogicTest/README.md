# Solution as Follows :

````javascript
function maxDuffelBagValue(_cakeTypes, _wtCapacity) {
    var maxValCapacities = [];
    for (var i = 0; i <= _wtCapacity; i++) {
        maxValCapacities[i] = 0;
    }
    for (var capacityNow = 0; capacityNow <= _wtCapacity; capacityNow++) {
//         console.log(capacityNow);
        var currentMaxVal = 0;
        for(var cakeTypeKey in _cakeTypes){
            var cakeType = _cakeTypes[cakeTypeKey];
            if (cakeType.weight === 0 && cakeType.value !== 0) {
                return Infinity;
            }
//             console.log(cakeType);
            if (cakeType.weight <= capacityNow) {
				console.log(cakeType.value,maxValCapacities[capacityNow - cakeType.weight])

                var maxCakeVal = cakeType.value + maxValCapacities[capacityNow - cakeType.weight];
                currentMaxVal = Math.max(maxCakeVal, currentMaxVal);
            }
        }
		console.log(maxValCapacities);
        maxValCapacities[capacityNow] = currentMaxVal;
    }
    return maxValCapacities[_wtCapacity];
  }

var cakeTypes = [
  { weight: 7, value: 160 },
  { weight: 3, value: 90 },
  { weight: 2, value: 15 },
];

var capacity = 20;

var result = maxDuffelBagValue(cakeTypes, capacity);

console.log('Result ',result);
````
