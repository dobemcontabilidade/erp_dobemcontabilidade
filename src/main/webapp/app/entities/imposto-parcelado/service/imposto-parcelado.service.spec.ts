import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IImpostoParcelado } from '../imposto-parcelado.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../imposto-parcelado.test-samples';

import { ImpostoParceladoService } from './imposto-parcelado.service';

const requireRestSample: IImpostoParcelado = {
  ...sampleWithRequiredData,
};

describe('ImpostoParcelado Service', () => {
  let service: ImpostoParceladoService;
  let httpMock: HttpTestingController;
  let expectedResult: IImpostoParcelado | IImpostoParcelado[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(ImpostoParceladoService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a ImpostoParcelado', () => {
      const impostoParcelado = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(impostoParcelado).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ImpostoParcelado', () => {
      const impostoParcelado = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(impostoParcelado).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ImpostoParcelado', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ImpostoParcelado', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ImpostoParcelado', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addImpostoParceladoToCollectionIfMissing', () => {
      it('should add a ImpostoParcelado to an empty array', () => {
        const impostoParcelado: IImpostoParcelado = sampleWithRequiredData;
        expectedResult = service.addImpostoParceladoToCollectionIfMissing([], impostoParcelado);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(impostoParcelado);
      });

      it('should not add a ImpostoParcelado to an array that contains it', () => {
        const impostoParcelado: IImpostoParcelado = sampleWithRequiredData;
        const impostoParceladoCollection: IImpostoParcelado[] = [
          {
            ...impostoParcelado,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addImpostoParceladoToCollectionIfMissing(impostoParceladoCollection, impostoParcelado);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ImpostoParcelado to an array that doesn't contain it", () => {
        const impostoParcelado: IImpostoParcelado = sampleWithRequiredData;
        const impostoParceladoCollection: IImpostoParcelado[] = [sampleWithPartialData];
        expectedResult = service.addImpostoParceladoToCollectionIfMissing(impostoParceladoCollection, impostoParcelado);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(impostoParcelado);
      });

      it('should add only unique ImpostoParcelado to an array', () => {
        const impostoParceladoArray: IImpostoParcelado[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const impostoParceladoCollection: IImpostoParcelado[] = [sampleWithRequiredData];
        expectedResult = service.addImpostoParceladoToCollectionIfMissing(impostoParceladoCollection, ...impostoParceladoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const impostoParcelado: IImpostoParcelado = sampleWithRequiredData;
        const impostoParcelado2: IImpostoParcelado = sampleWithPartialData;
        expectedResult = service.addImpostoParceladoToCollectionIfMissing([], impostoParcelado, impostoParcelado2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(impostoParcelado);
        expect(expectedResult).toContain(impostoParcelado2);
      });

      it('should accept null and undefined values', () => {
        const impostoParcelado: IImpostoParcelado = sampleWithRequiredData;
        expectedResult = service.addImpostoParceladoToCollectionIfMissing([], null, impostoParcelado, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(impostoParcelado);
      });

      it('should return initial array if no ImpostoParcelado is added', () => {
        const impostoParceladoCollection: IImpostoParcelado[] = [sampleWithRequiredData];
        expectedResult = service.addImpostoParceladoToCollectionIfMissing(impostoParceladoCollection, undefined, null);
        expect(expectedResult).toEqual(impostoParceladoCollection);
      });
    });

    describe('compareImpostoParcelado', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareImpostoParcelado(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareImpostoParcelado(entity1, entity2);
        const compareResult2 = service.compareImpostoParcelado(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareImpostoParcelado(entity1, entity2);
        const compareResult2 = service.compareImpostoParcelado(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareImpostoParcelado(entity1, entity2);
        const compareResult2 = service.compareImpostoParcelado(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
