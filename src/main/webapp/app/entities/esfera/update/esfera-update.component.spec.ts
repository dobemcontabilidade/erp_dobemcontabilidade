import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { EsferaService } from '../service/esfera.service';
import { IEsfera } from '../esfera.model';
import { EsferaFormService } from './esfera-form.service';

import { EsferaUpdateComponent } from './esfera-update.component';

describe('Esfera Management Update Component', () => {
  let comp: EsferaUpdateComponent;
  let fixture: ComponentFixture<EsferaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let esferaFormService: EsferaFormService;
  let esferaService: EsferaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [EsferaUpdateComponent],
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
      .overrideTemplate(EsferaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EsferaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    esferaFormService = TestBed.inject(EsferaFormService);
    esferaService = TestBed.inject(EsferaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const esfera: IEsfera = { id: 456 };

      activatedRoute.data = of({ esfera });
      comp.ngOnInit();

      expect(comp.esfera).toEqual(esfera);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEsfera>>();
      const esfera = { id: 123 };
      jest.spyOn(esferaFormService, 'getEsfera').mockReturnValue(esfera);
      jest.spyOn(esferaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ esfera });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: esfera }));
      saveSubject.complete();

      // THEN
      expect(esferaFormService.getEsfera).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(esferaService.update).toHaveBeenCalledWith(expect.objectContaining(esfera));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEsfera>>();
      const esfera = { id: 123 };
      jest.spyOn(esferaFormService, 'getEsfera').mockReturnValue({ id: null });
      jest.spyOn(esferaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ esfera: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: esfera }));
      saveSubject.complete();

      // THEN
      expect(esferaFormService.getEsfera).toHaveBeenCalled();
      expect(esferaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEsfera>>();
      const esfera = { id: 123 };
      jest.spyOn(esferaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ esfera });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(esferaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
