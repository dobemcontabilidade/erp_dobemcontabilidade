import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IParcelamentoImposto } from 'app/entities/parcelamento-imposto/parcelamento-imposto.model';
import { ParcelamentoImpostoService } from 'app/entities/parcelamento-imposto/service/parcelamento-imposto.service';
import { ParcelaImpostoAPagarService } from '../service/parcela-imposto-a-pagar.service';
import { IParcelaImpostoAPagar } from '../parcela-imposto-a-pagar.model';
import { ParcelaImpostoAPagarFormService } from './parcela-imposto-a-pagar-form.service';

import { ParcelaImpostoAPagarUpdateComponent } from './parcela-imposto-a-pagar-update.component';

describe('ParcelaImpostoAPagar Management Update Component', () => {
  let comp: ParcelaImpostoAPagarUpdateComponent;
  let fixture: ComponentFixture<ParcelaImpostoAPagarUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let parcelaImpostoAPagarFormService: ParcelaImpostoAPagarFormService;
  let parcelaImpostoAPagarService: ParcelaImpostoAPagarService;
  let parcelamentoImpostoService: ParcelamentoImpostoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ParcelaImpostoAPagarUpdateComponent],
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
      .overrideTemplate(ParcelaImpostoAPagarUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ParcelaImpostoAPagarUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    parcelaImpostoAPagarFormService = TestBed.inject(ParcelaImpostoAPagarFormService);
    parcelaImpostoAPagarService = TestBed.inject(ParcelaImpostoAPagarService);
    parcelamentoImpostoService = TestBed.inject(ParcelamentoImpostoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call ParcelamentoImposto query and add missing value', () => {
      const parcelaImpostoAPagar: IParcelaImpostoAPagar = { id: 456 };
      const parcelamentoImposto: IParcelamentoImposto = { id: 21423 };
      parcelaImpostoAPagar.parcelamentoImposto = parcelamentoImposto;

      const parcelamentoImpostoCollection: IParcelamentoImposto[] = [{ id: 32514 }];
      jest.spyOn(parcelamentoImpostoService, 'query').mockReturnValue(of(new HttpResponse({ body: parcelamentoImpostoCollection })));
      const additionalParcelamentoImpostos = [parcelamentoImposto];
      const expectedCollection: IParcelamentoImposto[] = [...additionalParcelamentoImpostos, ...parcelamentoImpostoCollection];
      jest.spyOn(parcelamentoImpostoService, 'addParcelamentoImpostoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ parcelaImpostoAPagar });
      comp.ngOnInit();

      expect(parcelamentoImpostoService.query).toHaveBeenCalled();
      expect(parcelamentoImpostoService.addParcelamentoImpostoToCollectionIfMissing).toHaveBeenCalledWith(
        parcelamentoImpostoCollection,
        ...additionalParcelamentoImpostos.map(expect.objectContaining),
      );
      expect(comp.parcelamentoImpostosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const parcelaImpostoAPagar: IParcelaImpostoAPagar = { id: 456 };
      const parcelamentoImposto: IParcelamentoImposto = { id: 17016 };
      parcelaImpostoAPagar.parcelamentoImposto = parcelamentoImposto;

      activatedRoute.data = of({ parcelaImpostoAPagar });
      comp.ngOnInit();

      expect(comp.parcelamentoImpostosSharedCollection).toContain(parcelamentoImposto);
      expect(comp.parcelaImpostoAPagar).toEqual(parcelaImpostoAPagar);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IParcelaImpostoAPagar>>();
      const parcelaImpostoAPagar = { id: 123 };
      jest.spyOn(parcelaImpostoAPagarFormService, 'getParcelaImpostoAPagar').mockReturnValue(parcelaImpostoAPagar);
      jest.spyOn(parcelaImpostoAPagarService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ parcelaImpostoAPagar });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: parcelaImpostoAPagar }));
      saveSubject.complete();

      // THEN
      expect(parcelaImpostoAPagarFormService.getParcelaImpostoAPagar).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(parcelaImpostoAPagarService.update).toHaveBeenCalledWith(expect.objectContaining(parcelaImpostoAPagar));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IParcelaImpostoAPagar>>();
      const parcelaImpostoAPagar = { id: 123 };
      jest.spyOn(parcelaImpostoAPagarFormService, 'getParcelaImpostoAPagar').mockReturnValue({ id: null });
      jest.spyOn(parcelaImpostoAPagarService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ parcelaImpostoAPagar: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: parcelaImpostoAPagar }));
      saveSubject.complete();

      // THEN
      expect(parcelaImpostoAPagarFormService.getParcelaImpostoAPagar).toHaveBeenCalled();
      expect(parcelaImpostoAPagarService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IParcelaImpostoAPagar>>();
      const parcelaImpostoAPagar = { id: 123 };
      jest.spyOn(parcelaImpostoAPagarService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ parcelaImpostoAPagar });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(parcelaImpostoAPagarService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareParcelamentoImposto', () => {
      it('Should forward to parcelamentoImpostoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(parcelamentoImpostoService, 'compareParcelamentoImposto');
        comp.compareParcelamentoImposto(entity, entity2);
        expect(parcelamentoImpostoService.compareParcelamentoImposto).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
