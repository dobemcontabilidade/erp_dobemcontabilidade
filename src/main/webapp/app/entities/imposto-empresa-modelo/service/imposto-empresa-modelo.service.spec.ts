import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IImpostoEmpresaModelo } from '../imposto-empresa-modelo.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../imposto-empresa-modelo.test-samples';

import { ImpostoEmpresaModeloService } from './imposto-empresa-modelo.service';

const requireRestSample: IImpostoEmpresaModelo = {
  ...sampleWithRequiredData,
};

describe('ImpostoEmpresaModelo Service', () => {
  let service: ImpostoEmpresaModeloService;
  let httpMock: HttpTestingController;
  let expectedResult: IImpostoEmpresaModelo | IImpostoEmpresaModelo[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(ImpostoEmpresaModeloService);
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

    it('should create a ImpostoEmpresaModelo', () => {
      const impostoEmpresaModelo = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(impostoEmpresaModelo).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ImpostoEmpresaModelo', () => {
      const impostoEmpresaModelo = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(impostoEmpresaModelo).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ImpostoEmpresaModelo', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ImpostoEmpresaModelo', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ImpostoEmpresaModelo', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addImpostoEmpresaModeloToCollectionIfMissing', () => {
      it('should add a ImpostoEmpresaModelo to an empty array', () => {
        const impostoEmpresaModelo: IImpostoEmpresaModelo = sampleWithRequiredData;
        expectedResult = service.addImpostoEmpresaModeloToCollectionIfMissing([], impostoEmpresaModelo);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(impostoEmpresaModelo);
      });

      it('should not add a ImpostoEmpresaModelo to an array that contains it', () => {
        const impostoEmpresaModelo: IImpostoEmpresaModelo = sampleWithRequiredData;
        const impostoEmpresaModeloCollection: IImpostoEmpresaModelo[] = [
          {
            ...impostoEmpresaModelo,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addImpostoEmpresaModeloToCollectionIfMissing(impostoEmpresaModeloCollection, impostoEmpresaModelo);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ImpostoEmpresaModelo to an array that doesn't contain it", () => {
        const impostoEmpresaModelo: IImpostoEmpresaModelo = sampleWithRequiredData;
        const impostoEmpresaModeloCollection: IImpostoEmpresaModelo[] = [sampleWithPartialData];
        expectedResult = service.addImpostoEmpresaModeloToCollectionIfMissing(impostoEmpresaModeloCollection, impostoEmpresaModelo);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(impostoEmpresaModelo);
      });

      it('should add only unique ImpostoEmpresaModelo to an array', () => {
        const impostoEmpresaModeloArray: IImpostoEmpresaModelo[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const impostoEmpresaModeloCollection: IImpostoEmpresaModelo[] = [sampleWithRequiredData];
        expectedResult = service.addImpostoEmpresaModeloToCollectionIfMissing(impostoEmpresaModeloCollection, ...impostoEmpresaModeloArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const impostoEmpresaModelo: IImpostoEmpresaModelo = sampleWithRequiredData;
        const impostoEmpresaModelo2: IImpostoEmpresaModelo = sampleWithPartialData;
        expectedResult = service.addImpostoEmpresaModeloToCollectionIfMissing([], impostoEmpresaModelo, impostoEmpresaModelo2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(impostoEmpresaModelo);
        expect(expectedResult).toContain(impostoEmpresaModelo2);
      });

      it('should accept null and undefined values', () => {
        const impostoEmpresaModelo: IImpostoEmpresaModelo = sampleWithRequiredData;
        expectedResult = service.addImpostoEmpresaModeloToCollectionIfMissing([], null, impostoEmpresaModelo, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(impostoEmpresaModelo);
      });

      it('should return initial array if no ImpostoEmpresaModelo is added', () => {
        const impostoEmpresaModeloCollection: IImpostoEmpresaModelo[] = [sampleWithRequiredData];
        expectedResult = service.addImpostoEmpresaModeloToCollectionIfMissing(impostoEmpresaModeloCollection, undefined, null);
        expect(expectedResult).toEqual(impostoEmpresaModeloCollection);
      });
    });

    describe('compareImpostoEmpresaModelo', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareImpostoEmpresaModelo(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareImpostoEmpresaModelo(entity1, entity2);
        const compareResult2 = service.compareImpostoEmpresaModelo(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareImpostoEmpresaModelo(entity1, entity2);
        const compareResult2 = service.compareImpostoEmpresaModelo(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareImpostoEmpresaModelo(entity1, entity2);
        const compareResult2 = service.compareImpostoEmpresaModelo(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
