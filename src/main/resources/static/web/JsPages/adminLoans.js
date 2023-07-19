const { createApp } = Vue

const app = createApp({
  data() {
  return {
    logged: false,
    object:{
        name: "",
        maxAmount:"",
        payments:[],
        interest:0
      },
      payments:null
      };
  },

  created(){
    this.loadPayments()
  },

  methods:{
    logOut(){
      axios.post('/api/logout')
      .then(response => window.location.href="../htmlPages/login.html")
    },

    loadPayments(){
        axios.get("/api/loans")
        .then(res=> {
            console.log(res)
            this.payments = res.data.map(p => p.payments)
            
        })
        .catch(err => console.log(err))
    },

    createLoan(){
        this.object.interest = parseFloat(this.object.interest)
        this.object.payments = Array.from(JSON.parse(this.object.payments))
        console.log(this.object)
        axios.post('/api/loans/adminLoans',this.object)
        .then(res=>{
            console.log(res)
        })
        .catch(err => console.log(err))
    }
  }

})
  .mount('#app')