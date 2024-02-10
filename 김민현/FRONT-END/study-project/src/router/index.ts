import {route} from 'quasar/wrappers';
import {
  createMemoryHistory,
  createRouter,
  createWebHashHistory,
  createWebHistory,
} from 'vue-router/auto';


// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignore
import {setupLayouts} from 'virtual:generated-layouts'


// import routes from './routes';


export default route(function (/* { store, ssrContext } */) {
  const createHistory = process.env.SERVER
    ? createMemoryHistory
    : (process.env.VUE_ROUTER_MODE === 'history' ? createWebHistory : createWebHashHistory);

  const Router = createRouter({
    scrollBehavior: () => ({ left: 0, top: 0 }),
    // routes,
    history: createHistory(process.env.VUE_ROUTER_BASE),
    extendRoutes: routes => {
      return setupLayouts(routes.map(route=>{
        if(route.path.includes('admin')){
          route = {
            ...route,
            meta: {
              ...route.meta,
              layout:'admin'
            }
          }
        }
        return route;
      }));
    }

  });

  return Router;
});
