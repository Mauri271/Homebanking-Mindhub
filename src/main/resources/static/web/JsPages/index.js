const { createApp } = Vue

const app = createApp({
  data() {
  return {
    logged: false
      };
  },

  created(){
    axios.get('/api/clients/current')
    .then(response => {
     if(response.status == 200){
      this.logged = true
     } else if (response.status != 200 || response.status !== 201){
      this.logged = false
     }
    })
    .catch(err => { console.log(err)})
  },

  methods:{
    logOut(){
      axios.post('/api/logout')
      .then(response => window.location.href="../htmlPages/login.html")
    }
  }

})
  .mount('#app')
  