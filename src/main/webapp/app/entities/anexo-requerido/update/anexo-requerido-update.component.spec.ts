import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { AnexoRequeridoService } from '../service/anexo-requerido.service';
import { IAnexoRequerido } from '../anexo-requerido.model';
import { AnexoRequeridoFormService } from './anexo-requerido-form.service';

import { AnexoRequeridoUpdateComponent } from './anexo-requerido-update.component';

describe('AnexoRequerido Management Update Component', () => {
  let comp: AnexoRequeridoUpdateComponent;
  let fixture: ComponentFixture<AnexoRequeridoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let anexoRequeridoFormService: AnexoRequeridoFormService;
  let anexoRequeridoService: AnexoRequeridoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AnexoRequeridoUpdateComponent],
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
      .overrideTemplate(AnexoRequeridoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AnexoRequeridoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    anexoRequeridoFormService = TestBed.inject(AnexoRequeridoFormService);
    anexoRequeridoService = TestBed.inject(AnexoRequeridoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const anexoRequerido: IAnexoRequerido = { id: 456 };

      activatedRoute.data = of({ anexoRequerido });
      comp.ngOnInit();

      expect(comp.anexoRequerido).toEqual(anexoRequerido);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAnexoRequerido>>();
      const anexoRequerido = { id: 123 };
      jest.spyOn(anexoRequeridoFormService, 'getAnexoRequerido').mockReturnValue(anexoRequerido);
      jest.spyOn(anexoRequeridoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ anexoRequerido });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: anexoRequerido }));
      saveSubject.complete();

      // THEN
      expect(anexoRequeridoFormService.getAnexoRequerido).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(anexoRequeridoService.update).toHaveBeenCalledWith(expect.objectContaining(anexoRequerido));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAnexoRequerido>>();
      const anexoRequerido = { id: 123 };
      jest.spyOn(anexoRequeridoFormService, 'getAnexoRequerido').mockReturnValue({ id: null });
      jest.spyOn(anexoRequeridoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ anexoRequerido: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: anexoRequerido }));
      saveSubject.complete();

      // THEN
      expect(anexoRequeridoFormService.getAnexoRequerido).toHaveBeenCalled();
      expect(anexoRequeridoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAnexoRequerido>>();
      const anexoRequerido = { id: 123 };
      jest.spyOn(anexoRequeridoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ anexoRequerido });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(anexoRequeridoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
