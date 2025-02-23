class ProductionItem:
    def __init__(self, lhs: str, rhs: list, dot_position: int):
        self.lhs = lhs
        self.rhs = rhs
        self.dot_position = dot_position

    def __eq__(self, other):
        return self.rhs == other.rhs and \
               self.lhs == other.lhs and \
               self.dot_position == other.dot_position

    def __str__(self):
        result = f"[{self.lhs} -> "

        for idx in range(len(self.rhs)):
            if idx == self.dot_position:
                result += ". "
            result += self.rhs[idx] + " "

        if self.dot_position == len(self.rhs):
            result += "."

        return result.strip() + "]"
