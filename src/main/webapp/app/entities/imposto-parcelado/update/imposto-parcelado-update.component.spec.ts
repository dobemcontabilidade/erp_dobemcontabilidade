import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IParcelamentoImposto } from 'app/entities/parcelamento-imposto/parcelamento-imposto.model';
import { ParcelamentoImpostoService } from 'app/entities/parcelamento-imposto/service/parcelamento-imposto.service';
import { IImpostoAPagarEmpresa } from 'app/entities/imposto-a-pagar-empresa/imposto-a-pagar-empresa.model';
import { ImpostoAPagarEmpresaService } from 'app/entities/imposto-a-pagar-empresa/service/imposto-a-pagar-empresa.service';
import { IImpostoParcelado } from '../imposto-parcelado.model';
import { ImpostoParceladoService } from '../service/imposto-parcelado.service';
import { ImpostoParceladoFormService } from './imposto-parcelado-form.service';

import { ImpostoParceladoUpdateComponent } from './imposto-parcelado-update.component';

describe('ImpostoParcelado Management Update Component', () => {
  let comp: ImpostoParceladoUpdateComponent;
  let fixture: ComponentFixture<ImpostoParceladoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let impostoParceladoFormService: ImpostoParceladoFormService;
  let impostoParceladoService: ImpostoParceladoService;
  let parcelamentoImpostoService: ParcelamentoImpostoService;
  let impostoAPagarEmpresaService: ImpostoAPagarEmpresaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ImpostoParceladoUpdateComponent],
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
      .overrideTemplate(ImpostoParceladoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ImpostoParceladoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    impostoParceladoFormService = TestBed.inject(ImpostoParceladoFormService);
    impostoParceladoService = TestBed.inject(ImpostoParceladoService);
    parcelamentoImpostoService = TestBed.inject(ParcelamentoImpostoService);
    impostoAPagarEmpresaService = TestBed.inject(ImpostoAPagarEmpresaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call ParcelamentoImposto query and add missing value', () => {
      const impostoParcelado: IImpostoParcelado = { id: 456 };
      const parcelamentoImposto: IParcelamentoImposto = { id: 20056 };
      impostoParcelado.parcelamentoImposto = parcelamentoImposto;

      const parcelamentoImpostoCollection: IParcelamentoImposto[] = [{ id: 24204 }];
      jest.spyOn(parcelamentoImpostoService, 'query').mockReturnValue(of(new HttpResponse({ body: parcelamentoImpostoCollection })));
      const additionalParcelamentoImpostos = [parcelamentoImposto];
      const expectedCollection: IParcelamentoImposto[] = [...additionalParcelamentoImpostos, ...parcelamentoImpostoCollection];
      jest.spyOn(parcelamentoImpostoService, 'addParcelamentoImpostoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ impostoParcelado });
      comp.ngOnInit();

      expect(parcelamentoImpostoService.query).toHaveBeenCalled();
      expect(parcelamentoImpostoService.addParcelamentoImpostoToCollectionIfMissing).toHaveBeenCalledWith(
        parcelamentoImpostoCollection,
        ...additionalParcelamentoImpostos.map(expect.objectContaining),
      );
      expect(comp.parcelamentoImpostosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call ImpostoAPagarEmpresa query and add missing value', () => {
      const impostoParcelado: IImpostoParcelado = { id: 456 };
      const impostoAPagarEmpresa: IImpostoAPagarEmpresa = { id: 655 };
      impostoParcelado.impostoAPagarEmpresa = impostoAPagarEmpresa;

      const impostoAPagarEmpresaCollection: IImpostoAPagarEmpresa[] = [{ id: 29538 }];
      jest.spyOn(impostoAPagarEmpresaService, 'query').mockReturnValue(of(new HttpResponse({ body: impostoAPagarEmpresaCollection })));
      const additionalImpostoAPagarEmpresas = [impostoAPagarEmpresa];
      const expectedCollection: IImpostoAPagarEmpresa[] = [...additionalImpostoAPagarEmpresas, ...impostoAPagarEmpresaCollection];
      jest.spyOn(impostoAPagarEmpresaService, 'addImpostoAPagarEmpresaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ impostoParcelado });
      comp.ngOnInit();

      expect(impostoAPagarEmpresaService.query).toHaveBeenCalled();
      expect(impostoAPagarEmpresaService.addImpostoAPagarEmpresaToCollectionIfMissing).toHaveBeenCalledWith(
        impostoAPagarEmpresaCollection,
        ...additionalImpostoAPagarEmpresas.map(expect.objectContaining),
      );
      expect(comp.impostoAPagarEmpresasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const impostoParcelado: IImpostoParcelado = { id: 456 };
      const parcelamentoImposto: IParcelamentoImposto = { id: 9362 };
      impostoParcelado.parcelamentoImposto = parcelamentoImposto;
      const impostoAPagarEmpresa: IImpostoAPagarEmpresa = { id: 30329 };
      impostoParcelado.impostoAPagarEmpresa = impostoAPagarEmpresa;

      activatedRoute.data = of({ impostoParcelado });
      comp.ngOnInit();

      expect(comp.parcelamentoImpostosSharedCollection).toContain(parcelamentoImposto);
      expect(comp.impostoAPagarEmpresasSharedCollection).toContain(impostoAPagarEmpresa);
      expect(comp.impostoParcelado).toEqual(impostoParcelado);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IImpostoParcelado>>();
      const impostoParcelado = { id: 123 };
      jest.spyOn(impostoParceladoFormService, 'getImpostoParcelado').mockReturnValue(impostoParcelado);
      jest.spyOn(impostoParceladoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ impostoParcelado });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: impostoParcelado }));
      saveSubject.complete();

      // THEN
      expect(impostoParceladoFormService.getImpostoParcelado).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(impostoParceladoService.update).toHaveBeenCalledWith(expect.objectContaining(impostoParcelado));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IImpostoParcelado>>();
      const impostoParcelado = { id: 123 };
      jest.spyOn(impostoParceladoFormService, 'getImpostoParcelado').mockReturnValue({ id: null });
      jest.spyOn(impostoParceladoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ impostoParcelado: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: impostoParcelado }));
      saveSubject.complete();

      // THEN
      expect(impostoParceladoFormService.getImpostoParcelado).toHaveBeenCalled();
      expect(impostoParceladoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IImpostoParcelado>>();
      const impostoParcelado = { id: 123 };
      jest.spyOn(impostoParceladoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ impostoParcelado });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(impostoParceladoService.update).toHaveBeenCalled();
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

    describe('compareImpostoAPagarEmpresa', () => {
      it('Should forward to impostoAPagarEmpresaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(impostoAPagarEmpresaService, 'compareImpostoAPagarEmpresa');
        comp.compareImpostoAPagarEmpresa(entity, entity2);
        expect(impostoAPagarEmpresaService.compareImpostoAPagarEmpresa).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
