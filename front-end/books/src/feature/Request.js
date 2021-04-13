import { useState, useEffect } from "react"
import axios from "axios"

export const useAxiosGet = (url, data=null, header=null) => {
  const [response, setResponse] = useState(null);

  axios.get(url, {
    data,
    header
  })
  .then(res => setResponse(res.data))
  .catch(err => console.log(err.response))

  return response
}