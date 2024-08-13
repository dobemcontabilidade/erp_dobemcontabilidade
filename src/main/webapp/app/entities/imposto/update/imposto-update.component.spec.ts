import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IEsfera } from 'app/entities/esfera/esfera.model';
import { EsferaService } from 'app/entities/esfera/service/esfera.service';
import { ImpostoService } from '../service/imposto.service';
import { IImposto } from '../imposto.model';
import { ImpostoFormService } from './imposto-form.service';

import { ImpostoUpdateComponent } from './imposto-update.component';

describe('Imposto Management Update Component', () => {
  let comp: ImpostoUpdateComponent;
  let fixture: ComponentFixture<ImpostoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let impostoFormService: ImpostoFormService;
  let impostoService: ImpostoService;
  let esferaService: EsferaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ImpostoUpdateComponent],
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
      .overrideTemplate(ImpostoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ImpostoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    impostoFormService = TestBed.inject(ImpostoFormService);
    impostoService = TestBed.inject(ImpostoService);
    esferaService = TestBed.inject(EsferaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Esfera query and add missing value', () => {
      const imposto: IImposto = { id: 456 };
      const esfera: IEsfera = { id: 8828 };
      imposto.esfera = esfera;

      const esferaCollection: IEsfera[] = [{ id: 16065 }];
      jest.spyOn(esferaService, 'query').mockReturnValue(of(new HttpResponse({ body: esferaCollection })));
      const additionalEsferas = [esfera];
      const expectedCollection: IEsfera[] = [...additionalEsferas, ...esferaCollection];
      jest.spyOn(esferaService, 'addEsferaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ imposto });
      comp.ngOnInit();

      expect(esferaService.query).toHaveBeenCalled();
      expect(esferaService.addEsferaToCollectionIfMissing).toHaveBeenCalledWith(
        esferaCollection,
        ...additionalEsferas.map(expect.objectContaining),
      );
      expect(comp.esferasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const imposto: IImposto = { id: 456 };
      const esfera: IEsfera = { id: 19434 };
      imposto.esfera = esfera;

      activatedRoute.data = of({ imposto });
      comp.ngOnInit();

      expect(comp.esferasSharedCollection).toContain(esfera);
      expect(comp.imposto).toEqual(imposto);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IImposto>>();
      const imposto = { id: 123 };
      jest.spyOn(impostoFormService, 'getImposto').mockReturnValue(imposto);
      jest.spyOn(impostoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ imposto });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: imposto }));
      saveSubject.complete();

      // THEN
      expect(impostoFormService.getImposto).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(impostoService.update).toHaveBeenCalledWith(expect.objectContaining(imposto));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IImposto>>();
      const imposto = { id: 123 };
      jest.spyOn(impostoFormService, 'getImposto').mockReturnValue({ id: null });
      jest.spyOn(impostoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ imposto: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: imposto }));
      saveSubject.complete();

      // THEN
      expect(impostoFormService.getImposto).toHaveBeenCalled();
      expect(impostoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IImposto>>();
      const imposto = { id: 123 };
      jest.spyOn(impostoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ imposto });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(impostoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareEsfera', () => {
      it('Should forward to esferaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(esferaService, 'compareEsfera');
        comp.compareEsfera(entity, entity2);
        expect(esferaService.compareEsfera).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
