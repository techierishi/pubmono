<template>
  <div>
    <mt-card title="ABABAB" minTitle="ABABAB column ABABAB， data ABABABABABAB，ABABABcardABABAB table ABABAB" table>
      <mt-table :column="column" :data="data"></mt-table>
    </mt-card>
    <mt-card title="ABABAB" minTitle="ABABAB striped ABABAB" table>
      <mt-table :column="column" :data="data" striped></mt-table>
    </mt-card>
    <mt-card title="ABABAB" minTitle="ABABAB bordered ABABAB" table>
      <mt-table :column="column" :data="data" bordered></mt-table>
    </mt-card>
    <mt-card title="Hover ABABAB" minTitle="ABABAB hover ABABAB" table>
      <mt-table :column="column" :data="data" hover></mt-table>
    </mt-card>
    <mt-card title="ABABAB" minTitle="ABABAB state ABABAB" table>
      <mt-table :column="column" :data="stateData"></mt-table>
    </mt-card>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        column: [
          {
            name: '#',
            key: 'num'
          },
          {
            name: 'ABABAB',
            key: 'name'
          }
          ,
          {
            name: 'ABABAB',
            key: 'ename'
          }
          ,
          {
            name: 'ABABAB',
            key: 'price'
          }
          ,
          {
            name: 'ABABAB',
            key: 'have'
          }
        ],
        data: [
          {
            num: 1,
            name: 'ABABAB',
            ename: 'Apple',
            price: '￥2',
            have: 1000
          },
          {
            num: 2,
            name: 'ABABAB',
            ename: 'Orange',
            price: '￥2.5',
            have: 4100
          },
          {
            num: 3,
            name: 'ABABAB',
            ename: 'Pear',
            price: '￥3',
            have: 7422
          },
          {
            num: 4,
            name: 'ABABAB',
            ename: 'Banana',
            price: '￥5',
            have: 8000
          },
          {
            num: 5,
            name: 'ABABAB',
            ename: 'Pitaya',
            price: '￥8',
            have: 5214
          },
          {
            num: 6,
            name: 'ABABAB',
            ename: 'Lemon',
            price: '￥10',
            have: 200
          },
          {
            num: 7,
            name: 'ABABAB',
            ename: 'grape',
            price: '￥15',
            have: 300
          }
        ],
        stateData: [
          {
            num: 1,
            name: 'ABABAB',
            ename: 'Apple',
            price: '￥2',
            have: 1000
          },
          {
            num: 2,
            name: 'ABABAB',
            ename: 'Orange',
            price: '￥2.5',
            have: 4100
          },
          {
            num: 3,
            name: 'ABABAB',
            ename: 'Pear',
            price: '￥3',
            have: 7422
          },
          {
            num: 4,
            name: 'ABABAB',
            ename: 'Banana',
            price: '￥5',
            have: 8000
          },
          {
            num: 5,
            name: 'ABABAB',
            ename: 'Pitaya',
            price: '￥8',
            have: 5214,
            tdState: 'success'
          },
          {
            num: 6,
            name: 'ABABAB',
            ename: 'Lemon',
            price: '￥10',
            have: 200,
            tdState: 'warning'
          },
          {
            num: 7,
            name: 'ABABAB',
            ename: 'grape',
            price: '￥15',
            have: 300,
            tdState: 'error'
          }
        ]
      }
    },
  }
</script>

<style scoped>

</style>