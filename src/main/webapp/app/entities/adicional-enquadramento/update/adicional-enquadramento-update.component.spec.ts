import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IEnquadramento } from 'app/entities/enquadramento/enquadramento.model';
import { EnquadramentoService } from 'app/entities/enquadramento/service/enquadramento.service';
import { IPlanoAssinaturaContabil } from 'app/entities/plano-assinatura-contabil/plano-assinatura-contabil.model';
import { PlanoAssinaturaContabilService } from 'app/entities/plano-assinatura-contabil/service/plano-assinatura-contabil.service';
import { IAdicionalEnquadramento } from '../adicional-enquadramento.model';
import { AdicionalEnquadramentoService } from '../service/adicional-enquadramento.service';
import { AdicionalEnquadramentoFormService } from './adicional-enquadramento-form.service';

import { AdicionalEnquadramentoUpdateComponent } from './adicional-enquadramento-update.component';

describe('AdicionalEnquadramento Management Update Component', () => {
  let comp: AdicionalEnquadramentoUpdateComponent;
  let fixture: ComponentFixture<AdicionalEnquadramentoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let adicionalEnquadramentoFormService: AdicionalEnquadramentoFormService;
  let adicionalEnquadramentoService: AdicionalEnquadramentoService;
  let enquadramentoService: EnquadramentoService;
  let planoAssinaturaContabilService: PlanoAssinaturaContabilService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AdicionalEnquadramentoUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(AdicionalEnquadramentoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AdicionalEnquadramentoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    adicionalEnquadramentoFormService = TestBed.inject(AdicionalEnquadramentoFormService);
    adicionalEnquadramentoService = TestBed.inject(AdicionalEnquadramentoService);
    enquadramentoService = TestBed.inject(EnquadramentoService);
    planoAssinaturaContabilService = TestBed.inject(PlanoAssinaturaContabilService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Enquadramento query and add missing value', () => {
      const adicionalEnquadramento: IAdicionalEnquadramento = { id: 456 };
      const enquadramento: IEnquadramento = { id: 19760 };
      adicionalEnquadramento.enquadramento = enquadramento;

      const enquadramentoCollection: IEnquadramento[] = [{ id: 1293 }];
      jest.spyOn(enquadramentoService, 'query').mockReturnValue(of(new HttpResponse({ body: enquadramentoCollection })));
      const additionalEnquadramentos = [enquadramento];
      const expectedCollection: IEnquadramento[] = [...additionalEnquadramentos, ...enquadramentoCollection];
      jest.spyOn(enquadramentoService, 'addEnquadramentoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ adicionalEnquadramento });
      comp.ngOnInit();

      expect(enquadramentoService.query).toHaveBeenCalled();
      expect(enquadramentoService.addEnquadramentoToCollectionIfMissing).toHaveBeenCalledWith(
        enquadramentoCollection,
        ...additionalEnquadramentos.map(expect.objectContaining),
      );
      expect(comp.enquadramentosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call PlanoAssinaturaContabil query and add missing value', () => {
      const adicionalEnquadramento: IAdicionalEnquadramento = { id: 456 };
      const planoAssinaturaContabil: IPlanoAssinaturaContabil = { id: 13219 };
      adicionalEnquadramento.planoAssinaturaContabil = planoAssinaturaContabil;

      const planoAssinaturaContabilCollection: IPlanoAssinaturaContabil[] = [{ id: 12088 }];
      jest
        .spyOn(planoAssinaturaContabilService, 'query')
        .mockReturnValue(of(new HttpResponse({ body: planoAssinaturaContabilCollection })));
      const additionalPlanoAssinaturaContabils = [planoAssinaturaContabil];
      const expectedCollection: IPlanoAssinaturaContabil[] = [...additionalPlanoAssinaturaContabils, ...planoAssinaturaContabilCollection];
      jest.spyOn(planoAssinaturaContabilService, 'addPlanoAssinaturaContabilToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ adicionalEnquadramento });
      comp.ngOnInit();

      expect(planoAssinaturaContabilService.query).toHaveBeenCalled();
      expect(planoAssinaturaContabilService.addPlanoAssinaturaContabilToCollectionIfMissing).toHaveBeenCalledWith(
        planoAssinaturaContabilCollection,
        ...additionalPlanoAssinaturaContabils.map(expect.objectContaining),
      );
      expect(comp.planoAssinaturaContabilsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const adicionalEnquadramento: IAdicionalEnquadramento = { id: 456 };
      const enquadramento: IEnquadramento = { id: 2583 };
      adicionalEnquadramento.enquadramento = enquadramento;
      const planoAssinaturaContabil: IPlanoAssinaturaContabil = { id: 15589 };
      adicionalEnquadramento.planoAssinaturaContabil = planoAssinaturaContabil;

      activatedRoute.data = of({ adicionalEnquadramento });
      comp.ngOnInit();

      expect(comp.enquadramentosSharedCollection).toContain(enquadramento);
      expect(comp.planoAssinaturaContabilsSharedCollection).toContain(planoAssinaturaContabil);
      expect(comp.adicionalEnquadramento).toEqual(adicionalEnquadramento);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAdicionalEnquadramento>>();
      const adicionalEnquadramento = { id: 123 };
      jest.spyOn(adicionalEnquadramentoFormService, 'getAdicionalEnquadramento').mockReturnValue(adicionalEnquadramento);
      jest.spyOn(adicionalEnquadramentoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ adicionalEnquadramento });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: adicionalEnquadramento }));
      saveSubject.complete();

      // THEN
      expect(adicionalEnquadramentoFormService.getAdicionalEnquadramento).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(adicionalEnquadramentoService.update).toHaveBeenCalledWith(expect.objectContaining(adicionalEnquadramento));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAdicionalEnquadramento>>();
      const adicionalEnquadramento = { id: 123 };
      jest.spyOn(adicionalEnquadramentoFormService, 'getAdicionalEnquadramento').mockReturnValue({ id: null });
      jest.spyOn(adicionalEnquadramentoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ adicionalEnquadramento: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: adicionalEnquadramento }));
      saveSubject.complete();

      // THEN
      expect(adicionalEnquadramentoFormService.getAdicionalEnquadramento).toHaveBeenCalled();
      expect(adicionalEnquadramentoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAdicionalEnquadramento>>();
      const adicionalEnquadramento = { id: 123 };
      jest.spyOn(adicionalEnquadramentoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ adicionalEnquadramento });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(adicionalEnquadramentoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareEnquadramento', () => {
      it('Should forward to enquadramentoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(enquadramentoService, 'compareEnquadramento');
        comp.compareEnquadramento(entity, entity2);
        expect(enquadramentoService.compareEnquadramento).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('comparePlanoAssinaturaContabil', () => {
      it('Should forward to planoAssinaturaContabilService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(planoAssinaturaContabilService, 'comparePlanoAssinaturaContabil');
        comp.comparePlanoAssinaturaContabil(entity, entity2);
        expect(planoAssinaturaContabilService.comparePlanoAssinaturaContabil).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
