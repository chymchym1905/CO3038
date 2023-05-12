import unittest
import uart

class Test:
    def testcom(input, expect):
        return input==expect

class test(unittest.TestCase):
    def testcom(self):
        input = "C"
        expect = uart.getPort()
        self.assertTrue(Test.testcom(input, expect))

if __name__ == "__main__":
    unittest.main()