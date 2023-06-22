const { createApp } = Vue

createApp({
  data() {
  return {
        cards: {},
      };
  },

  created(){
    this.loadData()
  },

  methods:{

  loadData(){
          axios.get("http://localhost:8080/api/clients/1")
          .then(res => {            
            
          this.cards= res.data.cards.sort((a,b) => a.id - b.id)
          console.log(this.cards)

          })
          .catch(err=> console.log(err))
  },




  }



}).mount('#app')