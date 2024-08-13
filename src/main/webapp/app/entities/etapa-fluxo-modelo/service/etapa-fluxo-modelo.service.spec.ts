import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IEtapaFluxoModelo } from '../etapa-fluxo-modelo.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../etapa-fluxo-modelo.test-samples';

import { EtapaFluxoModeloService } from './etapa-fluxo-modelo.service';

const requireRestSample: IEtapaFluxoModelo = {
  ...sampleWithRequiredData,
};

describe('EtapaFluxoModelo Service', () => {
  let service: EtapaFluxoModeloService;
  let httpMock: HttpTestingController;
  let expectedResult: IEtapaFluxoModelo | IEtapaFluxoModelo[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(EtapaFluxoModeloService);
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

    it('should create a EtapaFluxoModelo', () => {
      const etapaFluxoModelo = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(etapaFluxoModelo).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a EtapaFluxoModelo', () => {
      const etapaFluxoModelo = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(etapaFluxoModelo).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a EtapaFluxoModelo', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of EtapaFluxoModelo', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a EtapaFluxoModelo', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addEtapaFluxoModeloToCollectionIfMissing', () => {
      it('should add a EtapaFluxoModelo to an empty array', () => {
        const etapaFluxoModelo: IEtapaFluxoModelo = sampleWithRequiredData;
        expectedResult = service.addEtapaFluxoModeloToCollectionIfMissing([], etapaFluxoModelo);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(etapaFluxoModelo);
      });

      it('should not add a EtapaFluxoModelo to an array that contains it', () => {
        const etapaFluxoModelo: IEtapaFluxoModelo = sampleWithRequiredData;
        const etapaFluxoModeloCollection: IEtapaFluxoModelo[] = [
          {
            ...etapaFluxoModelo,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addEtapaFluxoModeloToCollectionIfMissing(etapaFluxoModeloCollection, etapaFluxoModelo);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a EtapaFluxoModelo to an array that doesn't contain it", () => {
        const etapaFluxoModelo: IEtapaFluxoModelo = sampleWithRequiredData;
        const etapaFluxoModeloCollection: IEtapaFluxoModelo[] = [sampleWithPartialData];
        expectedResult = service.addEtapaFluxoModeloToCollectionIfMissing(etapaFluxoModeloCollection, etapaFluxoModelo);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(etapaFluxoModelo);
      });

      it('should add only unique EtapaFluxoModelo to an array', () => {
        const etapaFluxoModeloArray: IEtapaFluxoModelo[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const etapaFluxoModeloCollection: IEtapaFluxoModelo[] = [sampleWithRequiredData];
        expectedResult = service.addEtapaFluxoModeloToCollectionIfMissing(etapaFluxoModeloCollection, ...etapaFluxoModeloArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const etapaFluxoModelo: IEtapaFluxoModelo = sampleWithRequiredData;
        const etapaFluxoModelo2: IEtapaFluxoModelo = sampleWithPartialData;
        expectedResult = service.addEtapaFluxoModeloToCollectionIfMissing([], etapaFluxoModelo, etapaFluxoModelo2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(etapaFluxoModelo);
        expect(expectedResult).toContain(etapaFluxoModelo2);
      });

      it('should accept null and undefined values', () => {
        const etapaFluxoModelo: IEtapaFluxoModelo = sampleWithRequiredData;
        expectedResult = service.addEtapaFluxoModeloToCollectionIfMissing([], null, etapaFluxoModelo, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(etapaFluxoModelo);
      });

      it('should return initial array if no EtapaFluxoModelo is added', () => {
        const etapaFluxoModeloCollection: IEtapaFluxoModelo[] = [sampleWithRequiredData];
        expectedResult = service.addEtapaFluxoModeloToCollectionIfMissing(etapaFluxoModeloCollection, undefined, null);
        expect(expectedResult).toEqual(etapaFluxoModeloCollection);
      });
    });

    describe('compareEtapaFluxoModelo', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareEtapaFluxoModelo(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareEtapaFluxoModelo(entity1, entity2);
        const compareResult2 = service.compareEtapaFluxoModelo(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareEtapaFluxoModelo(entity1, entity2);
        const compareResult2 = service.compareEtapaFluxoModelo(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareEtapaFluxoModelo(entity1, entity2);
        const compareResult2 = service.compareEtapaFluxoModelo(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
