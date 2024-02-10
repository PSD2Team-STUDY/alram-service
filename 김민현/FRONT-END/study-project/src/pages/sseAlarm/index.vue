<template>
  <div>
    <div>
      <div v-if="eventSource != null">
        연결 된 SSE URL:
        {{ eventSource.url }}
        <br>
        연결 상태 :
        {{ eventSource.readyState }}
      </div>
      <hr>
      {{ message }}
    </div>
    <div class="row">
      <q-input v-model="connectSseKey" label="연결 그룹을 입력하세요"></q-input>
      <q-btn @click="connectSse"> SSE 연결</q-btn>
    </div>
    <div class="row">
      <q-input v-model="sendSseKey" label="알람을 보낼 그룹을 입력하세요"></q-input>
      <q-btn @click="sendSse"> SSE 알람 전송</q-btn>
    </div>
  </div>
</template>

<script setup lang="ts">

import {ref} from 'vue';
import {useAxios} from '@vueuse/integrations/useAxios';
import {api} from 'boot/axios';

const connectSseKey = ref('connectSseKey');
const sendSseKey = ref('sendSseKey');
const message = ref(true)
const eventSource = ref();

const {execute: axiosExecute} = useAxios('/api/alerts',
  {method: 'GET'},
  api, {
    immediate: false,
  })

const connectSse = () => {
  try {
    if (eventSource.value != null) {
      eventSource.value.close();
      alert('close ok')
    }

    eventSource.value = new EventSource(`http://localhost:8080/alerts/${connectSseKey.value}`);
    eventSource.value.onmessage = function (event: any) {
      //메세지 수신 처리 로직
      message.value = !message.value
    };
    eventSource.value.onopen = function (event: any) {
      //연결 성공 처리 로직
      console.log('Connection to server opened.');
    };

    eventSource.value.onerror = function (event: any) {
      //에러 발생시 처리 로직
      console.log('Error occurred:', event);
    };
  } catch (e) {
    alert('연결 실패')

  }

}

const sendSse = () => {
  axiosExecute({params: {groupId: sendSseKey.value}})
}
</script>

<style scoped>

</style>
