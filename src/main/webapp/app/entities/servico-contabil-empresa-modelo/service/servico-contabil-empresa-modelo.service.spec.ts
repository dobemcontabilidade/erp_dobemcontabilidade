import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IServicoContabilEmpresaModelo } from '../servico-contabil-empresa-modelo.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../servico-contabil-empresa-modelo.test-samples';

import { ServicoContabilEmpresaModeloService } from './servico-contabil-empresa-modelo.service';

const requireRestSample: IServicoContabilEmpresaModelo = {
  ...sampleWithRequiredData,
};

describe('ServicoContabilEmpresaModelo Service', () => {
  let service: ServicoContabilEmpresaModeloService;
  let httpMock: HttpTestingController;
  let expectedResult: IServicoContabilEmpresaModelo | IServicoContabilEmpresaModelo[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(ServicoContabilEmpresaModeloService);
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

    it('should create a ServicoContabilEmpresaModelo', () => {
      const servicoContabilEmpresaModelo = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(servicoContabilEmpresaModelo).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ServicoContabilEmpresaModelo', () => {
      const servicoContabilEmpresaModelo = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(servicoContabilEmpresaModelo).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ServicoContabilEmpresaModelo', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ServicoContabilEmpresaModelo', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ServicoContabilEmpresaModelo', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addServicoContabilEmpresaModeloToCollectionIfMissing', () => {
      it('should add a ServicoContabilEmpresaModelo to an empty array', () => {
        const servicoContabilEmpresaModelo: IServicoContabilEmpresaModelo = sampleWithRequiredData;
        expectedResult = service.addServicoContabilEmpresaModeloToCollectionIfMissing([], servicoContabilEmpresaModelo);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(servicoContabilEmpresaModelo);
      });

      it('should not add a ServicoContabilEmpresaModelo to an array that contains it', () => {
        const servicoContabilEmpresaModelo: IServicoContabilEmpresaModelo = sampleWithRequiredData;
        const servicoContabilEmpresaModeloCollection: IServicoContabilEmpresaModelo[] = [
          {
            ...servicoContabilEmpresaModelo,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addServicoContabilEmpresaModeloToCollectionIfMissing(
          servicoContabilEmpresaModeloCollection,
          servicoContabilEmpresaModelo,
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ServicoContabilEmpresaModelo to an array that doesn't contain it", () => {
        const servicoContabilEmpresaModelo: IServicoContabilEmpresaModelo = sampleWithRequiredData;
        const servicoContabilEmpresaModeloCollection: IServicoContabilEmpresaModelo[] = [sampleWithPartialData];
        expectedResult = service.addServicoContabilEmpresaModeloToCollectionIfMissing(
          servicoContabilEmpresaModeloCollection,
          servicoContabilEmpresaModelo,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(servicoContabilEmpresaModelo);
      });

      it('should add only unique ServicoContabilEmpresaModelo to an array', () => {
        const servicoContabilEmpresaModeloArray: IServicoContabilEmpresaModelo[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const servicoContabilEmpresaModeloCollection: IServicoContabilEmpresaModelo[] = [sampleWithRequiredData];
        expectedResult = service.addServicoContabilEmpresaModeloToCollectionIfMissing(
          servicoContabilEmpresaModeloCollection,
          ...servicoContabilEmpresaModeloArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const servicoContabilEmpresaModelo: IServicoContabilEmpresaModelo = sampleWithRequiredData;
        const servicoContabilEmpresaModelo2: IServicoContabilEmpresaModelo = sampleWithPartialData;
        expectedResult = service.addServicoContabilEmpresaModeloToCollectionIfMissing(
          [],
          servicoContabilEmpresaModelo,
          servicoContabilEmpresaModelo2,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(servicoContabilEmpresaModelo);
        expect(expectedResult).toContain(servicoContabilEmpresaModelo2);
      });

      it('should accept null and undefined values', () => {
        const servicoContabilEmpresaModelo: IServicoContabilEmpresaModelo = sampleWithRequiredData;
        expectedResult = service.addServicoContabilEmpresaModeloToCollectionIfMissing([], null, servicoContabilEmpresaModelo, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(servicoContabilEmpresaModelo);
      });

      it('should return initial array if no ServicoContabilEmpresaModelo is added', () => {
        const servicoContabilEmpresaModeloCollection: IServicoContabilEmpresaModelo[] = [sampleWithRequiredData];
        expectedResult = service.addServicoContabilEmpresaModeloToCollectionIfMissing(
          servicoContabilEmpresaModeloCollection,
          undefined,
          null,
        );
        expect(expectedResult).toEqual(servicoContabilEmpresaModeloCollection);
      });
    });

    describe('compareServicoContabilEmpresaModelo', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareServicoContabilEmpresaModelo(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareServicoContabilEmpresaModelo(entity1, entity2);
        const compareResult2 = service.compareServicoContabilEmpresaModelo(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareServicoContabilEmpresaModelo(entity1, entity2);
        const compareResult2 = service.compareServicoContabilEmpresaModelo(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareServicoContabilEmpresaModelo(entity1, entity2);
        const compareResult2 = service.compareServicoContabilEmpresaModelo(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
