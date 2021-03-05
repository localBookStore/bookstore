import styled from "styled-components"
import { useState } from "react"

const CountBox = () => {
  const [count, setCount] = useState(0);

  const Upper = () => {
    setCount(count === 999 ? 999: count+1)
  }
  const Lower = () => {
    setCount(count === 0 ? 0 : count-1)
  }
  const inputValue = (e) => {
    const value = e.target.value;
    if (isFinite(value) && value*1 < 1000){
      setCount(value*1)
    } else {
      setCount(count)
    }
  }

  return <Counter>
    <Button onClick={Lower}>-</Button>
    <Number type="text" value={count} onChange={e => inputValue(e)} />
    <Button onClick={Upper}>+</Button>
  </Counter>
}
export default CountBox;


const Counter = styled.div`
  position: absolute;
  bottom: 53px;
  left: 710px;
`
const Button = styled.button`
  border: 1 solid #4CAF50;
  background-color: white;
  border-radius: 5px;

  color: green;
  font-size:16px;
  font-weight: 500;
  &:hover {
    background-color: #4CAF50;
    color: white;
  }
`

const Number = styled.input`
  margin:0 10px;
  text-align:center;
  width:90px;
`