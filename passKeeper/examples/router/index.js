import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/components/home'
import SavePass from '@/components/savepass'
import Cards from '@/components/cards'
import Input from '@/components/input'
import Buttons from '@/components/buttons'
import Check from '@/components/check'
import Table from '@/components/table'
import Alert from '@/components/alert'
import Dialogs from '@/components/dialogs'
import Colors from '@/components/colors'
import Icons from '@/components/icons'

Vue.use(Router)

const routes = [
  {
    path: '/savepass',
    name: 'SavePass',
    component: SavePass
  },
  {
    path: '/cards',
    name: 'Cards',
    component: Cards
  },
  {
    path: '/table',
    name: 'Table',
    component: Table
  },
  {
    path: '/alert',
    name: 'Alert',
    component: Alert
  },
  {
    path: '/dialogs',
    name: 'Dialogs',
    component: Dialogs
  },
  {
    path: '/input',
    name: 'Input',
    component: Input
  },
  {
    path: '/buttons',
    name: 'Buttons',
    component: Buttons
  },
  {
    path: '/check',
    name: 'Checkbox/Radio',
    component: Check
  },
  {
    path: '/colors',
    name: 'Colors',
    component: Colors
  },
  {
    path: '/icons',
    name: 'Icons',
    component: Icons
  },
  {
    path: '*',
    name: 'Home',
    component: Home
  },
]

export default new Router({
  mode: 'history',
  routes,
  scrollBehavior() {
    return {x: 0, y: 0}
  }
})
