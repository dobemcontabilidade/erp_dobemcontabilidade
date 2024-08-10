import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { AdicionalEnquadramentoDetailComponent } from './adicional-enquadramento-detail.component';

describe('AdicionalEnquadramento Management Detail Component', () => {
  let comp: AdicionalEnquadramentoDetailComponent;
  let fixture: ComponentFixture<AdicionalEnquadramentoDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdicionalEnquadramentoDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: AdicionalEnquadramentoDetailComponent,
              resolve: { adicionalEnquadramento: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(AdicionalEnquadramentoDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AdicionalEnquadramentoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load adicionalEnquadramento on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', AdicionalEnquadramentoDetailComponent);

      // THEN
      expect(instance.adicionalEnquadramento()).toEqual(expect.objectContaining({ id: 123 }));
    });
  });

  describe('PreviousState', () => {
    it('Should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
