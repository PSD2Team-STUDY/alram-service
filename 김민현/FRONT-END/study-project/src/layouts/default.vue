<template>
  <q-layout view="lHh Lpr lff" class="bg-grey-2">
    <q-header bordered class="bg-white text-grey-9">
      <q-toolbar>
        <q-btn flat dense to="/">
          <q-toolbar-title>
            <q-avatar>
              <q-icon name="alarm"></q-icon>
            </q-avatar>
            알람 서비스
          </q-toolbar-title>
        </q-btn>
        <q-space />
        <q-btn stretch flat label="Short Polling" to="/shortPolling" />
        <q-btn
          stretch
          flat
          label="Long Polling"
          to="/longPolling"
        />
        <q-btn
          stretch
          flat
          label="SSE"
          to="/sseAlarm"
        />
        <q-btn
          stretch
          flat
          label="webSocket"
          to="/webSocket"
        />
      </q-toolbar>
    </q-header>

    <q-page-container :style="pageContainerStyles">
      <router-view />
    </q-page-container>

  </q-layout>
</template>

<script setup>
import { computed, ref } from 'vue';
import { useRoute } from 'vue-router';


const route = useRoute();
// debugger;
// console.dir(route);
const pageContainerStyles = computed(() => ({
  maxWidth: route.meta?.width || '1080px',
  margin: '0 auto',
}));

const authDialog = ref(false);
const openAuthDialog = () => (authDialog.value = true);
</script>
