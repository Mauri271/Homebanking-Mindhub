const { createApp } = Vue

createApp({
  data() {
  return {
        clients: {},
        accounts:{},
        loans:{}
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
          this.accounts = this.clients.accounts.sort((a,b) => a.balance - b.balance);
          this.loans = this.clients.loans

          console.log(this.loans)

          })
          .catch(err=> console.log(err))
  },




  }



}).mount('#app')