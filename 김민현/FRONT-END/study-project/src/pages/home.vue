<template>
  <div>
    <div class="text-h3">Home</div>
    <div>
      {{isLoading}}
      {{$route.name }}
      {{isFinished}}
      {{data}}
      <q-btn @click="execute(0)">test</q-btn>
      <q-btn @click="axiosExcute">useAxios</q-btn>
      <q-btn @click="sendWebSocket">Web Socket Send</q-btn>

    </div>
  </div>
</template>

<script setup lang="ts">
import { getData } from 'src/services';
import { useAsyncState } from '@vueuse/core';
import { useAxios } from '@vueuse/integrations/useAxios';
import { api } from 'boot/axios';
import Stomp from "stompjs";

import {onMounted, ref} from "vue";
const WEBSOCKET_SERVER_URL = 'ws://localhost:8080/ws';

const Row = {
  id : 'a'
}
const { state: comments, execute , isLoading } = useAsyncState(
  () => getData(Row),
  undefined,
  {
    resetOnExecute: false,
  },
);

const {data, isFinished ,execute : axiosExcute} = useAxios('/api/articles', { method: 'POST' },
  api, {
  immediate: false,
})
onMounted(() => {
  // 소켓 연결
  socketConnect();
  }
)


const client = ref();
// WebSocket Server 연결
const socketConnect = () => {
  let newClient = Stomp.client(WEBSOCKET_SERVER_URL);
  newClient.debug = console.log; // 디버그 메시지 비활성화 null, 활성화 console.log

  // 연결 성공시 구독 함수 정의
  const onConnect = () => {
    console.log("웹소켓 연결완료");
    // 구독함수 정의
    const subscription = client.value.subscribe(
      '/sub/socket/role/mhkim', // 사용자에 따라 url을 변경 `/sub/socket/role/${SessionId}`,
      (message : any) => {
        // console.log("Received message:", message);
        console.dir('=========');
        console.dir(message);
        alert(message.body)
      }
    );
  };

  const onError = (error : any) => {
    console.error("웹소켓 연결 error:", error);
  };

  newClient.connect({}, onConnect, onError);
  client.value = newClient;

  return () => {
    newClient.disconnect();
  };


};

const sendWebSocket = async () => {
  try {

    const message = {
      type: "role",
      rtcSession: "mhkim",
      userId: "mhKim",
      message: "",
      data: {
        role: 2,
        state: 1,
        saveState: -1,
      },
    };

    client.value.send("/pub/socket/test", {}, JSON.stringify(message));
    console.log("메시지 보냈음");
  } catch (error) {
    console.log("Error sending message:", error);
  }
};
</script>

<style lang="scss" scoped>

</style>
<route lang="yaml">
meta:
  layout : admin
</route>
