import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IServicoContabilEtapaFluxoModelo } from '../servico-contabil-etapa-fluxo-modelo.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../servico-contabil-etapa-fluxo-modelo.test-samples';

import { ServicoContabilEtapaFluxoModeloService } from './servico-contabil-etapa-fluxo-modelo.service';

const requireRestSample: IServicoContabilEtapaFluxoModelo = {
  ...sampleWithRequiredData,
};

describe('ServicoContabilEtapaFluxoModelo Service', () => {
  let service: ServicoContabilEtapaFluxoModeloService;
  let httpMock: HttpTestingController;
  let expectedResult: IServicoContabilEtapaFluxoModelo | IServicoContabilEtapaFluxoModelo[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(ServicoContabilEtapaFluxoModeloService);
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

    it('should create a ServicoContabilEtapaFluxoModelo', () => {
      const servicoContabilEtapaFluxoModelo = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(servicoContabilEtapaFluxoModelo).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ServicoContabilEtapaFluxoModelo', () => {
      const servicoContabilEtapaFluxoModelo = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(servicoContabilEtapaFluxoModelo).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ServicoContabilEtapaFluxoModelo', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ServicoContabilEtapaFluxoModelo', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ServicoContabilEtapaFluxoModelo', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addServicoContabilEtapaFluxoModeloToCollectionIfMissing', () => {
      it('should add a ServicoContabilEtapaFluxoModelo to an empty array', () => {
        const servicoContabilEtapaFluxoModelo: IServicoContabilEtapaFluxoModelo = sampleWithRequiredData;
        expectedResult = service.addServicoContabilEtapaFluxoModeloToCollectionIfMissing([], servicoContabilEtapaFluxoModelo);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(servicoContabilEtapaFluxoModelo);
      });

      it('should not add a ServicoContabilEtapaFluxoModelo to an array that contains it', () => {
        const servicoContabilEtapaFluxoModelo: IServicoContabilEtapaFluxoModelo = sampleWithRequiredData;
        const servicoContabilEtapaFluxoModeloCollection: IServicoContabilEtapaFluxoModelo[] = [
          {
            ...servicoContabilEtapaFluxoModelo,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addServicoContabilEtapaFluxoModeloToCollectionIfMissing(
          servicoContabilEtapaFluxoModeloCollection,
          servicoContabilEtapaFluxoModelo,
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ServicoContabilEtapaFluxoModelo to an array that doesn't contain it", () => {
        const servicoContabilEtapaFluxoModelo: IServicoContabilEtapaFluxoModelo = sampleWithRequiredData;
        const servicoContabilEtapaFluxoModeloCollection: IServicoContabilEtapaFluxoModelo[] = [sampleWithPartialData];
        expectedResult = service.addServicoContabilEtapaFluxoModeloToCollectionIfMissing(
          servicoContabilEtapaFluxoModeloCollection,
          servicoContabilEtapaFluxoModelo,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(servicoContabilEtapaFluxoModelo);
      });

      it('should add only unique ServicoContabilEtapaFluxoModelo to an array', () => {
        const servicoContabilEtapaFluxoModeloArray: IServicoContabilEtapaFluxoModelo[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const servicoContabilEtapaFluxoModeloCollection: IServicoContabilEtapaFluxoModelo[] = [sampleWithRequiredData];
        expectedResult = service.addServicoContabilEtapaFluxoModeloToCollectionIfMissing(
          servicoContabilEtapaFluxoModeloCollection,
          ...servicoContabilEtapaFluxoModeloArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const servicoContabilEtapaFluxoModelo: IServicoContabilEtapaFluxoModelo = sampleWithRequiredData;
        const servicoContabilEtapaFluxoModelo2: IServicoContabilEtapaFluxoModelo = sampleWithPartialData;
        expectedResult = service.addServicoContabilEtapaFluxoModeloToCollectionIfMissing(
          [],
          servicoContabilEtapaFluxoModelo,
          servicoContabilEtapaFluxoModelo2,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(servicoContabilEtapaFluxoModelo);
        expect(expectedResult).toContain(servicoContabilEtapaFluxoModelo2);
      });

      it('should accept null and undefined values', () => {
        const servicoContabilEtapaFluxoModelo: IServicoContabilEtapaFluxoModelo = sampleWithRequiredData;
        expectedResult = service.addServicoContabilEtapaFluxoModeloToCollectionIfMissing(
          [],
          null,
          servicoContabilEtapaFluxoModelo,
          undefined,
        );
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(servicoContabilEtapaFluxoModelo);
      });

      it('should return initial array if no ServicoContabilEtapaFluxoModelo is added', () => {
        const servicoContabilEtapaFluxoModeloCollection: IServicoContabilEtapaFluxoModelo[] = [sampleWithRequiredData];
        expectedResult = service.addServicoContabilEtapaFluxoModeloToCollectionIfMissing(
          servicoContabilEtapaFluxoModeloCollection,
          undefined,
          null,
        );
        expect(expectedResult).toEqual(servicoContabilEtapaFluxoModeloCollection);
      });
    });

    describe('compareServicoContabilEtapaFluxoModelo', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareServicoContabilEtapaFluxoModelo(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareServicoContabilEtapaFluxoModelo(entity1, entity2);
        const compareResult2 = service.compareServicoContabilEtapaFluxoModelo(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareServicoContabilEtapaFluxoModelo(entity1, entity2);
        const compareResult2 = service.compareServicoContabilEtapaFluxoModelo(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareServicoContabilEtapaFluxoModelo(entity1, entity2);
        const compareResult2 = service.compareServicoContabilEtapaFluxoModelo(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
