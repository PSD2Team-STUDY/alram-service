import { api } from 'boot/axios';

interface Row {
  id : string
}
export  const getData = async (mapData : Row) => {
  await api.post('/api/articles', mapData)
    .then(() => {
      console.log('ok')
    }).catch((err) => {
    console.log(err);
  });
}

