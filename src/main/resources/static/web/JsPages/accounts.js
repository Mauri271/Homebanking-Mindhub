const { createApp } = Vue

createApp({
  data() {
  return {
        clients: [],
      };
  },

  created(){
    this.loadData()
  },

  methods:{

  loadData(){
          axios.get("http://localhost:8080/api/clients/1")
          .then(res => {            
            
          this.clients= res.data         
          console.log(this.clients)

          })
          .catch(err=> console.log(err))
  },




  }



}).mount('#app')